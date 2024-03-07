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

package net.consensys.linea.zktracer;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import lombok.extern.slf4j.Slf4j;
import net.consensys.linea.zktracer.testing.ToyExecutionEnvironment;
import org.junit.jupiter.api.Test;

/**
 * Replays are captured on a fully (not snapshot) synchronized Besu node running the plugin:
 *
 * <pre>{@code
 * curl -X POST 'http://localhost:8545'
 * --data '{
 *    "jsonrpc":"2.0",
 *    "method":"rollup_captureConflation",
 *    "params":["296519", "296521"], "id":"1"
 *  }'
 * | jq '.result.capture' -r
 * | gzip > arithmetization/src/test/resources/replays/my-test-case.json.gz
 * }</pre>
 */
@Slf4j
public class ReplayTests {
  /**
   * Loads a .json or .json.gz replay file generated by the {@link
   * net.consensys.linea.blockcapture.BlockCapturer} and execute it as a test.
   *
   * @param filename the file in resources/replays/ containing the replay
   */
  public static void replay(String filename) {
    final InputStream fileStream =
        ReplayTests.class.getClassLoader().getResourceAsStream("replays/%s".formatted(filename));
    if (fileStream == null) {
      fail("unable to find %s in replay resources".formatted(filename));
    }

    final InputStream stream;
    try {
      stream = filename.toLowerCase().endsWith("gz") ? new GZIPInputStream(fileStream) : fileStream;
    } catch (IOException e) {
      log.error("while loading {}: {}", filename, e.getMessage());
      throw new RuntimeException(e);
    }
    ToyExecutionEnvironment.builder()
        .build()
        .replay(new BufferedReader(new InputStreamReader(stream)));
  }

  @Test
  void traceTxStartNotTheSameAsTxPrepare() {
    replay("start-vs-prepare-tx.json.gz");
  }

  @Test
  void fatMxp() {
    replay("2492975-2492977.json.gz");
  }
}