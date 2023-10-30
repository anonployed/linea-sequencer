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

package net.consensys.linea.zktracer.module.add;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.consensys.linea.zktracer.ColumnHeader;
import net.consensys.linea.zktracer.types.EWord;
import net.consensys.linea.zktracer.types.UnsignedByte;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * WARNING: This code is generated automatically.
 * Any modifications to this code may be overwritten and could lead to unexpected behavior.
 * Please DO NOT ATTEMPT TO MODIFY this code directly.
 */
public record Trace(
  @JsonProperty("ACC_1") List<BigInteger> acc1,
  @JsonProperty("ACC_2") List<BigInteger> acc2,
  @JsonProperty("ARG_1_HI") List<BigInteger> arg1Hi,
  @JsonProperty("ARG_1_LO") List<BigInteger> arg1Lo,
  @JsonProperty("ARG_2_HI") List<BigInteger> arg2Hi,
  @JsonProperty("ARG_2_LO") List<BigInteger> arg2Lo,
  @JsonProperty("BYTE_1") List<UnsignedByte> byte1,
  @JsonProperty("BYTE_2") List<UnsignedByte> byte2,
  @JsonProperty("CT") List<BigInteger> ct,
  @JsonProperty("INST") List<BigInteger> inst,
  @JsonProperty("OVERFLOW") List<Boolean> overflow,
  @JsonProperty("RES_HI") List<BigInteger> resHi,
  @JsonProperty("RES_LO") List<BigInteger> resLo,
  @JsonProperty("STAMP") List<BigInteger> stamp) { 
  static TraceBuilder builder(int length) {
    return new TraceBuilder(length);
  }

  public int size() {
      return this.acc1.size();
  }

  public static List<ColumnHeader> headers(int size) {
    return List.of(
      new ColumnHeader("add.ACC_1", 32, size),
      new ColumnHeader("add.ACC_2", 32, size),
      new ColumnHeader("add.ARG_1_HI", 32, size),
      new ColumnHeader("add.ARG_1_LO", 32, size),
      new ColumnHeader("add.ARG_2_HI", 32, size),
      new ColumnHeader("add.ARG_2_LO", 32, size),
      new ColumnHeader("add.BYTE_1", 1, size),
      new ColumnHeader("add.BYTE_2", 1, size),
      new ColumnHeader("add.CT", 32, size),
      new ColumnHeader("add.INST", 32, size),
      new ColumnHeader("add.OVERFLOW", 1, size),
      new ColumnHeader("add.RES_HI", 32, size),
      new ColumnHeader("add.RES_LO", 32, size),
      new ColumnHeader("add.STAMP", 32, size)
      );
  }

  static class BufferTraceWriter {
    private final BitSet filled = new BitSet();
    private final int length;
    private int currentLength = 0;
    private final ByteBuffer target;

    public BufferTraceWriter(ByteBuffer target, int length) {
      this.length = length;
      this.target = target;
    }


    private void writeacc1(final BigInteger b) {
      this.target.put(0 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writeacc2(final BigInteger b) {
      this.target.put(32 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writearg1Hi(final BigInteger b) {
      this.target.put(64 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writearg1Lo(final BigInteger b) {
      this.target.put(96 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writearg2Hi(final BigInteger b) {
      this.target.put(128 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writearg2Lo(final BigInteger b) {
      this.target.put(160 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writebyte1(final UnsignedByte b) {
      this.target.put(192 * this.length + this.currentLength*1, b.toByte());
    }
    private void writebyte2(final UnsignedByte b) {
      this.target.put(193 * this.length + this.currentLength*1, b.toByte());
    }
    private void writect(final BigInteger b) {
      this.target.put(194 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writeinst(final BigInteger b) {
      this.target.put(226 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writeoverflow(final Boolean b) {
      this.target.put(258 * this.length + this.currentLength*1, (byte) (b ? 1 : 0));
    }
    private void writeresHi(final BigInteger b) {
      this.target.put(259 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writeresLo(final BigInteger b) {
      this.target.put(291 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }
    private void writestamp(final BigInteger b) {
      this.target.put(323 * this.length + this.currentLength*32, EWord.of(b).toArray());
    }


    public BufferTraceWriter acc1(final BigInteger b) {
      if (filled.get(0)) {
        throw new IllegalStateException("ACC_1 already set");
      } else {
        filled.set(0);
      }

      this.writeacc1(b);

      return this;
    }
    public BufferTraceWriter acc2(final BigInteger b) {
      if (filled.get(1)) {
        throw new IllegalStateException("ACC_2 already set");
      } else {
        filled.set(1);
      }

      this.writeacc2(b);

      return this;
    }
    public BufferTraceWriter arg1Hi(final BigInteger b) {
      if (filled.get(2)) {
        throw new IllegalStateException("ARG_1_HI already set");
      } else {
        filled.set(2);
      }

      this.writearg1Hi(b);

      return this;
    }
    public BufferTraceWriter arg1Lo(final BigInteger b) {
      if (filled.get(3)) {
        throw new IllegalStateException("ARG_1_LO already set");
      } else {
        filled.set(3);
      }

      this.writearg1Lo(b);

      return this;
    }
    public BufferTraceWriter arg2Hi(final BigInteger b) {
      if (filled.get(4)) {
        throw new IllegalStateException("ARG_2_HI already set");
      } else {
        filled.set(4);
      }

      this.writearg2Hi(b);

      return this;
    }
    public BufferTraceWriter arg2Lo(final BigInteger b) {
      if (filled.get(5)) {
        throw new IllegalStateException("ARG_2_LO already set");
      } else {
        filled.set(5);
      }

      this.writearg2Lo(b);

      return this;
    }
    public BufferTraceWriter byte1(final UnsignedByte b) {
      if (filled.get(6)) {
        throw new IllegalStateException("BYTE_1 already set");
      } else {
        filled.set(6);
      }

      this.writebyte1(b);

      return this;
    }
    public BufferTraceWriter byte2(final UnsignedByte b) {
      if (filled.get(7)) {
        throw new IllegalStateException("BYTE_2 already set");
      } else {
        filled.set(7);
      }

      this.writebyte2(b);

      return this;
    }
    public BufferTraceWriter ct(final BigInteger b) {
      if (filled.get(8)) {
        throw new IllegalStateException("CT already set");
      } else {
        filled.set(8);
      }

      this.writect(b);

      return this;
    }
    public BufferTraceWriter inst(final BigInteger b) {
      if (filled.get(9)) {
        throw new IllegalStateException("INST already set");
      } else {
        filled.set(9);
      }

      this.writeinst(b);

      return this;
    }
    public BufferTraceWriter overflow(final Boolean b) {
      if (filled.get(10)) {
        throw new IllegalStateException("OVERFLOW already set");
      } else {
        filled.set(10);
      }

      this.writeoverflow(b);

      return this;
    }
    public BufferTraceWriter resHi(final BigInteger b) {
      if (filled.get(11)) {
        throw new IllegalStateException("RES_HI already set");
      } else {
        filled.set(11);
      }

      this.writeresHi(b);

      return this;
    }
    public BufferTraceWriter resLo(final BigInteger b) {
      if (filled.get(12)) {
        throw new IllegalStateException("RES_LO already set");
      } else {
        filled.set(12);
      }

      this.writeresLo(b);

      return this;
    }
    public BufferTraceWriter stamp(final BigInteger b) {
      if (filled.get(13)) {
        throw new IllegalStateException("STAMP already set");
      } else {
        filled.set(13);
      }

      this.writestamp(b);

      return this;
    }

    public BufferTraceWriter validateRow() {
      if (!filled.get(0)) {
        throw new IllegalStateException("ACC_1 has not been filled");
      }

      if (!filled.get(1)) {
        throw new IllegalStateException("ACC_2 has not been filled");
      }

      if (!filled.get(2)) {
        throw new IllegalStateException("ARG_1_HI has not been filled");
      }

      if (!filled.get(3)) {
        throw new IllegalStateException("ARG_1_LO has not been filled");
      }

      if (!filled.get(4)) {
        throw new IllegalStateException("ARG_2_HI has not been filled");
      }

      if (!filled.get(5)) {
        throw new IllegalStateException("ARG_2_LO has not been filled");
      }

      if (!filled.get(6)) {
        throw new IllegalStateException("BYTE_1 has not been filled");
      }

      if (!filled.get(7)) {
        throw new IllegalStateException("BYTE_2 has not been filled");
      }

      if (!filled.get(8)) {
        throw new IllegalStateException("CT has not been filled");
      }

      if (!filled.get(9)) {
        throw new IllegalStateException("INST has not been filled");
      }

      if (!filled.get(10)) {
        throw new IllegalStateException("OVERFLOW has not been filled");
      }

      if (!filled.get(11)) {
        throw new IllegalStateException("RES_HI has not been filled");
      }

      if (!filled.get(12)) {
        throw new IllegalStateException("RES_LO has not been filled");
      }

      if (!filled.get(13)) {
        throw new IllegalStateException("STAMP has not been filled");
      }


      filled.clear();
      this.currentLength++;

      return this;
    }

    public BufferTraceWriter fillAndValidateRow() {
      filled.clear();
      this.currentLength++;
      return this;
    }
  }

  static class TraceBuilder {
    private final BitSet filled = new BitSet();

    @JsonProperty("ACC_1")
    private final List<BigInteger> acc1;
    @JsonProperty("ACC_2")
    private final List<BigInteger> acc2;
    @JsonProperty("ARG_1_HI")
    private final List<BigInteger> arg1Hi;
    @JsonProperty("ARG_1_LO")
    private final List<BigInteger> arg1Lo;
    @JsonProperty("ARG_2_HI")
    private final List<BigInteger> arg2Hi;
    @JsonProperty("ARG_2_LO")
    private final List<BigInteger> arg2Lo;
    @JsonProperty("BYTE_1")
    private final List<UnsignedByte> byte1;
    @JsonProperty("BYTE_2")
    private final List<UnsignedByte> byte2;
    @JsonProperty("CT")
    private final List<BigInteger> ct;
    @JsonProperty("INST")
    private final List<BigInteger> inst;
    @JsonProperty("OVERFLOW")
    private final List<Boolean> overflow;
    @JsonProperty("RES_HI")
    private final List<BigInteger> resHi;
    @JsonProperty("RES_LO")
    private final List<BigInteger> resLo;
    @JsonProperty("STAMP")
    private final List<BigInteger> stamp;

    TraceBuilder(int length) {
      this.acc1 = new ArrayList<>(length);
      this.acc2 = new ArrayList<>(length);
      this.arg1Hi = new ArrayList<>(length);
      this.arg1Lo = new ArrayList<>(length);
      this.arg2Hi = new ArrayList<>(length);
      this.arg2Lo = new ArrayList<>(length);
      this.byte1 = new ArrayList<>(length);
      this.byte2 = new ArrayList<>(length);
      this.ct = new ArrayList<>(length);
      this.inst = new ArrayList<>(length);
      this.overflow = new ArrayList<>(length);
      this.resHi = new ArrayList<>(length);
      this.resLo = new ArrayList<>(length);
      this.stamp = new ArrayList<>(length);
    }

    public int size() {
      if (!filled.isEmpty()) {
        throw new RuntimeException("Cannot measure a trace with a non-validated row.");
      }

      return this.acc1.size();
    }

    public TraceBuilder acc1(final BigInteger b) {
      if (filled.get(0)) {
        throw new IllegalStateException("ACC_1 already set");
      } else {
        filled.set(0);
      }

      acc1.add(b);

      return this;
    }

    public TraceBuilder acc2(final BigInteger b) {
      if (filled.get(1)) {
        throw new IllegalStateException("ACC_2 already set");
      } else {
        filled.set(1);
      }

      acc2.add(b);

      return this;
    }

    public TraceBuilder arg1Hi(final BigInteger b) {
      if (filled.get(2)) {
        throw new IllegalStateException("ARG_1_HI already set");
      } else {
        filled.set(2);
      }

      arg1Hi.add(b);

      return this;
    }

    public TraceBuilder arg1Lo(final BigInteger b) {
      if (filled.get(3)) {
        throw new IllegalStateException("ARG_1_LO already set");
      } else {
        filled.set(3);
      }

      arg1Lo.add(b);

      return this;
    }

    public TraceBuilder arg2Hi(final BigInteger b) {
      if (filled.get(4)) {
        throw new IllegalStateException("ARG_2_HI already set");
      } else {
        filled.set(4);
      }

      arg2Hi.add(b);

      return this;
    }

    public TraceBuilder arg2Lo(final BigInteger b) {
      if (filled.get(5)) {
        throw new IllegalStateException("ARG_2_LO already set");
      } else {
        filled.set(5);
      }

      arg2Lo.add(b);

      return this;
    }

    public TraceBuilder byte1(final UnsignedByte b) {
      if (filled.get(6)) {
        throw new IllegalStateException("BYTE_1 already set");
      } else {
        filled.set(6);
      }

      byte1.add(b);

      return this;
    }

    public TraceBuilder byte2(final UnsignedByte b) {
      if (filled.get(7)) {
        throw new IllegalStateException("BYTE_2 already set");
      } else {
        filled.set(7);
      }

      byte2.add(b);

      return this;
    }

    public TraceBuilder ct(final BigInteger b) {
      if (filled.get(8)) {
        throw new IllegalStateException("CT already set");
      } else {
        filled.set(8);
      }

      ct.add(b);

      return this;
    }

    public TraceBuilder inst(final BigInteger b) {
      if (filled.get(9)) {
        throw new IllegalStateException("INST already set");
      } else {
        filled.set(9);
      }

      inst.add(b);

      return this;
    }

    public TraceBuilder overflow(final Boolean b) {
      if (filled.get(10)) {
        throw new IllegalStateException("OVERFLOW already set");
      } else {
        filled.set(10);
      }

      overflow.add(b);

      return this;
    }

    public TraceBuilder resHi(final BigInteger b) {
      if (filled.get(11)) {
        throw new IllegalStateException("RES_HI already set");
      } else {
        filled.set(11);
      }

      resHi.add(b);

      return this;
    }

    public TraceBuilder resLo(final BigInteger b) {
      if (filled.get(12)) {
        throw new IllegalStateException("RES_LO already set");
      } else {
        filled.set(12);
      }

      resLo.add(b);

      return this;
    }

    public TraceBuilder stamp(final BigInteger b) {
      if (filled.get(13)) {
        throw new IllegalStateException("STAMP already set");
      } else {
        filled.set(13);
      }

      stamp.add(b);

      return this;
    }

    public TraceBuilder validateRow() {
      if (!filled.get(0)) {
        throw new IllegalStateException("ACC_1 has not been filled");
      }

      if (!filled.get(1)) {
        throw new IllegalStateException("ACC_2 has not been filled");
      }

      if (!filled.get(2)) {
        throw new IllegalStateException("ARG_1_HI has not been filled");
      }

      if (!filled.get(3)) {
        throw new IllegalStateException("ARG_1_LO has not been filled");
      }

      if (!filled.get(4)) {
        throw new IllegalStateException("ARG_2_HI has not been filled");
      }

      if (!filled.get(5)) {
        throw new IllegalStateException("ARG_2_LO has not been filled");
      }

      if (!filled.get(6)) {
        throw new IllegalStateException("BYTE_1 has not been filled");
      }

      if (!filled.get(7)) {
        throw new IllegalStateException("BYTE_2 has not been filled");
      }

      if (!filled.get(8)) {
        throw new IllegalStateException("CT has not been filled");
      }

      if (!filled.get(9)) {
        throw new IllegalStateException("INST has not been filled");
      }

      if (!filled.get(10)) {
        throw new IllegalStateException("OVERFLOW has not been filled");
      }

      if (!filled.get(11)) {
        throw new IllegalStateException("RES_HI has not been filled");
      }

      if (!filled.get(12)) {
        throw new IllegalStateException("RES_LO has not been filled");
      }

      if (!filled.get(13)) {
        throw new IllegalStateException("STAMP has not been filled");
      }


      filled.clear();

      return this;
    }

    public TraceBuilder fillAndValidateRow() {
      if (!filled.get(0)) {
          acc1.add(BigInteger.ZERO);
          this.filled.set(0);
      }
      if (!filled.get(1)) {
          acc2.add(BigInteger.ZERO);
          this.filled.set(1);
      }
      if (!filled.get(2)) {
          arg1Hi.add(BigInteger.ZERO);
          this.filled.set(2);
      }
      if (!filled.get(3)) {
          arg1Lo.add(BigInteger.ZERO);
          this.filled.set(3);
      }
      if (!filled.get(4)) {
          arg2Hi.add(BigInteger.ZERO);
          this.filled.set(4);
      }
      if (!filled.get(5)) {
          arg2Lo.add(BigInteger.ZERO);
          this.filled.set(5);
      }
      if (!filled.get(6)) {
          byte1.add(UnsignedByte.of(0));
          this.filled.set(6);
      }
      if (!filled.get(7)) {
          byte2.add(UnsignedByte.of(0));
          this.filled.set(7);
      }
      if (!filled.get(8)) {
          ct.add(BigInteger.ZERO);
          this.filled.set(8);
      }
      if (!filled.get(9)) {
          inst.add(BigInteger.ZERO);
          this.filled.set(9);
      }
      if (!filled.get(10)) {
          overflow.add(false);
          this.filled.set(10);
      }
      if (!filled.get(11)) {
          resHi.add(BigInteger.ZERO);
          this.filled.set(11);
      }
      if (!filled.get(12)) {
          resLo.add(BigInteger.ZERO);
          this.filled.set(12);
      }
      if (!filled.get(13)) {
          stamp.add(BigInteger.ZERO);
          this.filled.set(13);
      }

      return this.validateRow();
    }

    public Trace build() {
      if (!filled.isEmpty()) {
        throw new IllegalStateException("Cannot build trace with a non-validated row.");
      }

      return new Trace(
        acc1,
        acc2,
        arg1Hi,
        arg1Lo,
        arg2Hi,
        arg2Lo,
        byte1,
        byte2,
        ct,
        inst,
        overflow,
        resHi,
        resLo,
        stamp);
    }
  }
}
