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
package net.consensys.linea.config;

import static net.consensys.linea.config.LineaTransactionSelectorCliOptions.DEFAULT_MODULE_LIMIT_FILE_PATH;
import static net.consensys.linea.config.LineaTransactionSelectorCliOptions.MODULE_LIMIT_FILE_PATH;

import com.google.common.base.MoreObjects;
import picocli.CommandLine;

public class LineaTracerConfigurationCLiOptions {

  @CommandLine.Option(
      names = {MODULE_LIMIT_FILE_PATH},
      hidden = true,
      paramLabel = "<STRING>",
      description =
          "Path to the toml file containing the module limits (default: ${DEFAULT-VALUE})")
  private String moduleLimitFilePath = DEFAULT_MODULE_LIMIT_FILE_PATH;

  private LineaTracerConfigurationCLiOptions() {}

  /**
   * Create Linea cli options.
   *
   * @return the Linea cli options
   */
  public static LineaTracerConfigurationCLiOptions create() {
    return new LineaTracerConfigurationCLiOptions();
  }

  /**
   * Linea cli options from config.
   *
   * @param config the config
   * @return the Linea cli options
   */
  public static LineaTracerConfigurationCLiOptions fromConfig(
      final LineaTracerConfiguration config) {
    final LineaTracerConfigurationCLiOptions options = create();
    options.moduleLimitFilePath = config.moduleLimitsFilePath();
    return options;
  }

  /**
   * To domain object Linea factory configuration.
   *
   * @return the Linea factory configuration
   */
  public LineaTracerConfiguration toDomainObject() {
    return LineaTracerConfiguration.builder().moduleLimitsFilePath(moduleLimitFilePath).build();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add(MODULE_LIMIT_FILE_PATH, moduleLimitFilePath)
        .toString();
  }
}
