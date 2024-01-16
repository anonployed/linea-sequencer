/*
 * Copyright Consensys Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package net.consensys.linea.sequencer.txselection.selectors;

import static net.consensys.linea.sequencer.txselection.LineaTransactionSelectionResult.BLOCK_MODULE_LINE_COUNT_FULL;
import static net.consensys.linea.sequencer.txselection.LineaTransactionSelectionResult.TX_MODULE_LINE_COUNT_OVERFLOW;
import static org.hyperledger.besu.plugin.data.TransactionSelectionResult.SELECTED;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.consensys.linea.zktracer.ZkTracer;
import org.hyperledger.besu.datatypes.PendingTransaction;
import org.hyperledger.besu.datatypes.Transaction;
import org.hyperledger.besu.plugin.data.BlockBody;
import org.hyperledger.besu.plugin.data.BlockHeader;
import org.hyperledger.besu.plugin.data.TransactionProcessingResult;
import org.hyperledger.besu.plugin.data.TransactionSelectionResult;
import org.hyperledger.besu.plugin.services.tracer.BlockAwareOperationTracer;
import org.hyperledger.besu.plugin.services.txselection.PluginTransactionSelector;
import org.hyperledger.besu.plugin.services.txselection.TransactionEvaluationContext;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * This class implements TransactionSelector and provides a specific implementation for evaluating
 * transactions based on the number of trace lines per module created by a transaction. It checks if
 * adding a transaction to the block pushes the trace lines for a module over the limit.
 */
@Slf4j
public class TraceLineLimitTransactionSelector implements PluginTransactionSelector {
  private static final Marker BLOCK_LINE_COUNT_MARKER = MarkerFactory.getMarker("BLOCK_LINE_COUNT");
  private final ZkTracer zkTracer;
  private final String limitFilePath;
  private final Map<String, Integer> moduleLimits;
  private Map<String, Integer> prevCumulatedLineCount = Map.of();
  private Map<String, Integer> currCumulatedLineCount;

  public TraceLineLimitTransactionSelector(
      final Supplier<Map<String, Integer>> moduleLimitsProvider, final String limitFilePath) {
    moduleLimits = moduleLimitsProvider.get();
    zkTracer = new ZkTracerWithLog();
    zkTracer.traceStartConflation(1L);
    this.limitFilePath = limitFilePath;
  }

  /**
   * No checking is done pre-processing.
   *
   * @param evaluationContext The current selection context.
   * @return TransactionSelectionResult.SELECTED
   */
  @Override
  public TransactionSelectionResult evaluateTransactionPreProcessing(
      final TransactionEvaluationContext<? extends PendingTransaction> evaluationContext) {
    return SELECTED;
  }

  @Override
  public void onTransactionNotSelected(
      final TransactionEvaluationContext<? extends PendingTransaction> evaluationContext,
      final TransactionSelectionResult transactionSelectionResult) {
    log.info(
        "onTransactionNotSelected getModulesLineCount pre pop {}", zkTracer.getModulesLineCount());
    zkTracer.popTransaction(evaluationContext.getPendingTransaction());
    log.info(
        "onTransactionNotSelected getModulesLineCount post pop {}", zkTracer.getModulesLineCount());
  }

  @Override
  public void onTransactionSelected(
      final TransactionEvaluationContext<? extends PendingTransaction> evaluationContext,
      final TransactionProcessingResult processingResult) {
    prevCumulatedLineCount = currCumulatedLineCount;
    log.info("onTransactionSelected getModulesLineCount {}", zkTracer.getModulesLineCount());
  }

  /**
   * Checking the created trace lines is performed post-processing.
   *
   * @param evaluationContext The current selection context.
   * @param processingResult The result of the transaction processing.
   * @return BLOCK_MODULE_LINE_COUNT_FULL if the trace lines for a module are over the limit for the
   *     block, TX_MODULE_LINE_COUNT_OVERFLOW if the trace lines are over the limit for the single
   *     tx, otherwise SELECTED.
   */
  @Override
  public TransactionSelectionResult evaluateTransactionPostProcessing(
      final TransactionEvaluationContext<? extends PendingTransaction> evaluationContext,
      final TransactionProcessingResult processingResult) {

    // check that we are not exceeding line number for any module
    currCumulatedLineCount = zkTracer.getModulesLineCount();

    final Transaction transaction = evaluationContext.getPendingTransaction().getTransaction();

    log.atInfo()
        .setMessage("Tx {} line count per module: {}")
        .addArgument(transaction::getHash)
        .addArgument(this::logTxLineCount)
        .log();

    for (final var module : currCumulatedLineCount.keySet()) {
      final Integer moduleLineCountLimit = moduleLimits.get(module);
      if (moduleLineCountLimit == null) {
        final String errorMsg =
            "Module " + module + " does not exist in the limits file: " + limitFilePath;
        log.error(errorMsg);
        throw new RuntimeException(errorMsg);
      }

      final int cumulatedModuleLineCount = currCumulatedLineCount.get(module);
      final int txModuleLineCount =
          cumulatedModuleLineCount - prevCumulatedLineCount.getOrDefault(module, 0);

      if (txModuleLineCount > moduleLineCountLimit) {
        log.warn(
            "Tx {} line count for module {}={} is above the limit {}, removing from the txpool",
            transaction.getHash(),
            module,
            txModuleLineCount,
            moduleLineCountLimit);
        return TX_MODULE_LINE_COUNT_OVERFLOW;
      }

      if (cumulatedModuleLineCount > moduleLineCountLimit) {
        log.atInfo()
            .setMessage(
                "Cumulated line count for module {}={} is above the limit {}, stopping selection")
            .addArgument(module)
            .addArgument(cumulatedModuleLineCount)
            .addArgument(moduleLineCountLimit)
            .log();
        return BLOCK_MODULE_LINE_COUNT_FULL;
      }
    }
    return SELECTED;
  }

  @Override
  public BlockAwareOperationTracer getOperationTracer() {
    return zkTracer;
  }

  private String logTxLineCount() {
    return currCumulatedLineCount.entrySet().stream()
        .map(
            e ->
                // tx line count / cumulated line count / line count limit
                e.getKey()
                    + "="
                    + (e.getValue() - prevCumulatedLineCount.getOrDefault(e.getKey(), 0))
                    + "/"
                    + e.getValue()
                    + "/"
                    + moduleLimits.get(e.getKey()))
        .collect(Collectors.joining(",", "[", "]"));
  }

  private class ZkTracerWithLog extends ZkTracer {
    @Override
    public void traceEndBlock(final BlockHeader blockHeader, final BlockBody blockBody) {
      super.traceEndBlock(blockHeader, blockBody);
      log.atDebug()
          .addMarker(BLOCK_LINE_COUNT_MARKER)
          .addKeyValue("blockNumber", blockHeader::getNumber)
          .addKeyValue("blockHash", blockHeader::getBlockHash)
          .addKeyValue(
              "traceCounts",
              () ->
                  currCumulatedLineCount == null
                      ? "null"
                      : currCumulatedLineCount.entrySet().stream()
                          .sorted(Map.Entry.comparingByKey())
                          .map(e -> '"' + e.getKey() + "\":" + e.getValue())
                          .collect(Collectors.joining(",")))
          .log();
    }
  }
}
