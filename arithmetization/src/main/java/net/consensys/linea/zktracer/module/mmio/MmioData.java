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

package net.consensys.linea.zktracer.module.mmio;

import static net.consensys.linea.zktracer.module.mmio.MmioPatterns.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.consensys.linea.zktracer.runtime.callstack.CallStack;
import net.consensys.linea.zktracer.types.EWord;
import net.consensys.linea.zktracer.types.UnsignedByte;
import org.apache.tuweni.units.bigints.UInt256;

@Getter
@Setter
@Accessors(fluent = true)
class MmioData {
  private int cnA;
  private int cnB;
  private int cnC;

  private int indexA;
  private int indexB;
  private int indexC;
  // pointer into exo data
  private int indexX;

  private UnsignedByte[] valA;
  private UnsignedByte[] valB;
  private UnsignedByte[] valC;

  private UnsignedByte[] valANew;
  private UnsignedByte[] valBNew;
  private UnsignedByte[] valCNew;

  private EWord val;
  private UnsignedByte[] valHi;
  private UnsignedByte[] valLo;
  private UnsignedByte[] valX;

  private boolean bin1;
  private boolean bin2;
  private boolean bin3;
  private boolean bin4;
  private boolean bin5;

  private UInt256 pow2561;
  private UInt256 pow2562;

  private UInt256 acc1;
  private UInt256 acc2;
  private UInt256 acc3;
  private UInt256 acc4;
  private UInt256 acc5;
  private UInt256 acc6;

  UnsignedByte byteA(int counter) {
    return valA[counter];
  }

  UnsignedByte byteB(int counter) {
    return valB[counter];
  }

  UnsignedByte byteC(int counter) {
    return valC[counter];
  }

  UnsignedByte byteX(int counter) {
    return valX[counter];
  }

  UnsignedByte byteHi(int counter) {
    return valHi[counter];
  }

  UnsignedByte byteLo(int counter) {
    return valLo[counter];
  }

  void onePartialToOne(
      UnsignedByte sb,
      UnsignedByte tb,
      UInt256 acc1,
      UInt256 acc2,
      UnsignedByte sm,
      UnsignedByte tm,
      int size,
      int counter) {
    bin1 = plateau(tm.toInteger(), counter);
    bin2 = plateau(tm.toInteger() + size, counter);
    bin3 = plateau(sm.toInteger(), counter);
    bin4 = plateau(sm.toInteger() + size, counter);

    this.acc1 = isolateChunk(acc1, tb, bin1, bin2, counter);
    this.acc2 = isolateChunk(acc2, sb, bin3, bin4, counter);

    pow2561 = power(pow2561, bin2, counter);
  }

  void onePartialToTwo(
      UnsignedByte sb,
      UnsignedByte t1b,
      UnsignedByte t2b,
      UInt256 acc1,
      UInt256 acc2,
      UInt256 acc3,
      UInt256 acc4,
      UnsignedByte sm,
      UnsignedByte t1m,
      int size,
      int counter) {
    bin1 = plateau(t1m.toInteger(), counter);
    bin2 = plateau(t1m.toInteger() + size - 16, counter);
    bin3 = plateau(sm.toInteger(), counter);
    bin4 = plateau(sm.toInteger() + 16 - t1m.toInteger(), counter);
    bin5 = plateau(sm.toInteger() + size, counter);

    this.acc1 = isolateSuffix(acc1, bin1, t1b);
    this.acc2 = isolateSuffix(acc2, bin2, t2b);

    pow2561 = power(pow2561, bin2, counter);

    this.acc3 = isolateChunk(acc3, sb, bin3, bin4, counter);
    this.acc4 = isolateChunk(acc4, sb, bin4, bin5, counter);
  }

  void updateLimbsInMemory(final CallStack callStack) {
    callStack.get(cnA).pending().memory().updateLimb(indexA, valANew);
    callStack.get(cnA).pending().memory().updateLimb(indexB, valBNew);
    callStack.get(cnA).pending().memory().updateLimb(indexC, valCNew);
  }
}
