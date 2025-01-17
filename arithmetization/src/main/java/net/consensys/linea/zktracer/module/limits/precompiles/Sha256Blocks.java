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

package net.consensys.linea.zktracer.module.limits.precompiles;

import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Stack;

import lombok.RequiredArgsConstructor;
import net.consensys.linea.zktracer.ColumnHeader;
import net.consensys.linea.zktracer.module.Module;
import net.consensys.linea.zktracer.module.hub.Hub;
import net.consensys.linea.zktracer.opcode.OpCode;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.internal.Words;

@RequiredArgsConstructor
public final class Sha256Blocks implements Module {
  private final Hub hub;
  private final Stack<Integer> counts = new Stack<>();

  @Override
  public String moduleKey() {
    return "PRECOMPILE_SHA2_BLOCKS";
  }

  private static final int PRECOMPILE_BASE_GAS_FEE = 60;
  private static final int PRECOMPILE_GAS_FEE_PER_EWORD = 12;
  private static final int SHA256_BLOCKSIZE = 64 * 8;
  // The length of the data to be hashed is 2**64 maximum.
  private static final int SHA256_PADDING_LENGTH = 64;
  private static final int SHA256_NB_PADDED_ONE = 1;

  @Override
  public void enterTransaction() {
    counts.push(0);
  }

  @Override
  public void popTransaction() {
    counts.pop();
  }

  @Override
  public void tracePreOpcode(MessageFrame frame) {
    final OpCode opCode = hub.opCode();

    switch (opCode) {
      case CALL, STATICCALL, DELEGATECALL, CALLCODE -> {
        final Address target = Words.toAddress(frame.getStackItem(1));
        if (target.equals(Address.SHA256)) {
          long dataByteLength = 0;
          switch (opCode) {
            case CALL, CALLCODE -> dataByteLength = Words.clampedToLong(frame.getStackItem(4));
            case DELEGATECALL, STATICCALL ->
                dataByteLength = Words.clampedToLong(frame.getStackItem(3));
          }
          if (dataByteLength == 0) {
            return;
          } // skip trivial hash TODO: check the prover does skip it
          final int blockCount =
              (int)
                      (dataByteLength * 8
                          + SHA256_NB_PADDED_ONE
                          + SHA256_PADDING_LENGTH
                          + (SHA256_BLOCKSIZE - 1))
                  / SHA256_BLOCKSIZE;

          final long wordCount = (dataByteLength + 31) / 32;
          final long gasPaid = Words.clampedToLong(frame.getStackItem(0));
          final long gasNeeded = PRECOMPILE_BASE_GAS_FEE + PRECOMPILE_GAS_FEE_PER_EWORD * wordCount;

          if (gasPaid >= gasNeeded) {
            this.counts.push(this.counts.pop() + blockCount);
          }
        }
      }
      default -> {}
    }
  }

  @Override
  public int lineCount() {
    return this.counts.stream().mapToInt(x -> x).sum();
  }

  @Override
  public List<ColumnHeader> columnsHeaders() {
    throw new IllegalStateException("should never be called");
  }

  @Override
  public void commit(List<MappedByteBuffer> buffers) {
    throw new IllegalStateException("should never be called");
  }
}
