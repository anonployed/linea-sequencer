/*
 * Copyright ConsenSys AG.
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

package net.consensys.linea.zktracer.module.wcp;

import static net.consensys.linea.zktracer.module.Util.byteBits;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import net.consensys.linea.zktracer.OpCode;
import net.consensys.linea.zktracer.bytes.Bytes16;
import net.consensys.linea.zktracer.bytes.UnsignedByte;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;

class WcpData {
  @Getter private final boolean isOneLineInstruction;
  @Getter private final Bytes16 arg1Hi;
  @Getter private final Bytes16 arg1Lo;
  @Getter private final Bytes16 arg2Hi;
  @Getter private final Bytes16 arg2Lo;

  @Getter private final Bytes16 adjHi;

  @Getter private final Bytes16 adjLo;
  @Getter private final Boolean neg1;

  @Getter private final Boolean neg2;
  @Getter private Boolean bit1 = true;
  @Getter private Boolean bit2 = true;
  @Getter private final Boolean bit3;
  @Getter private final Boolean bit4;
  @Getter private final Boolean resLo;

  @Getter final List<Boolean> bits;

  public WcpData(OpCode opCode, Bytes32 arg1, Bytes32 arg2) {
    this.isOneLineInstruction = isOneLineInstruction(opCode);

    this.arg1Hi = Bytes16.wrap(arg1.slice(0, 16));
    this.arg1Lo = Bytes16.wrap(arg1.slice(16));
    this.arg2Hi = Bytes16.wrap(arg2.slice(0, 16));
    this.arg2Lo = Bytes16.wrap(arg2.slice(16));

    // Calculate Result Low
    resLo = calculateResLow(opCode, arg1, arg2);

    // Initiate negatives
    UnsignedByte msb1 = UnsignedByte.of(this.arg1Hi.get(0));
    UnsignedByte msb2 = UnsignedByte.of(this.arg2Hi.get(0));
    Boolean[] msb1Bits = byteBits(msb1);
    Boolean[] msb2Bits = byteBits(msb2);
    this.neg1 = msb1Bits[0];
    this.neg2 = msb2Bits[0];

    // Initiate bits
    bits = new ArrayList<>(16);
    Collections.addAll(bits, msb1Bits);
    Collections.addAll(bits, msb2Bits);

    // Set bit 1 and 2
    for (int i = 0; i < 16; i++) {
      if (arg1Hi.get(i) != arg2Hi.get(i)) {
        bit1 = false;
      }
      if (arg1Lo.get(i) != arg2Lo.get(i)) {
        bit2 = false;
      }
    }

    // Set bit 3 and AdjHi
    final BigInteger firstHi = arg1Hi.toUnsignedBigInteger();
    final BigInteger secondHi = arg2Hi.toUnsignedBigInteger();
    bit3 = firstHi.compareTo(secondHi) > 0;
    this.adjHi = calculateAdj(bit3, firstHi, secondHi);

    // Set bit 4 and AdjLo
    final BigInteger firstLo = arg1Lo.toUnsignedBigInteger();
    final BigInteger secondLo = arg2Lo.toUnsignedBigInteger();
    bit4 = firstLo.compareTo(secondLo) > 0;
    this.adjLo = calculateAdj(bit4, firstLo, secondLo);
  }

  public Boolean getResHi() {
    return false;
  }

  private boolean isOneLineInstruction(final OpCode opCode) {
    return opCode.isElementOf(OpCode.EQ, OpCode.ISZERO);
  }

  private boolean calculateResLow(OpCode opCode, Bytes32 arg1, Bytes32 arg2) {
    return switch (opCode) {
      case LT -> arg1.compareTo(arg2) < 0;
      case GT -> arg1.compareTo(arg2) > 0;
      case SLT -> arg1.toBigInteger().compareTo(arg2.toBigInteger()) < 0;
      case SGT -> arg1.toBigInteger().compareTo(arg2.toBigInteger()) > 0;
      case EQ -> arg1.compareTo(arg2) == 0;
      case ISZERO -> arg1.isZero();
      default -> throw new InvalidParameterException("Invalid opcode");
    };
  }

  private Bytes16 calculateAdj(boolean cmp, BigInteger arg1, BigInteger arg2) {
    BigInteger adjHi;
    if (cmp) {
      adjHi = arg1.subtract(arg2).subtract(BigInteger.ONE);
    } else {
      adjHi = arg2.subtract(arg1);
    }
    var bytes32 = Bytes32.leftPad(Bytes.of(adjHi.toByteArray()));

    return Bytes16.wrap(bytes32.slice(16));
  }
}