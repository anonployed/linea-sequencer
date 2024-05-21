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

package net.consensys.linea.zktracer.module;

import net.consensys.linea.zktracer.testing.BytecodeRunner;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;

public class EcDataTest {
  @Test
  void testEcData() {
    BytecodeRunner.of(
            Bytes.fromHexString(
                "608060405234801561001057600080fd5b5061004a6001601b6001620f00007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe610b9760201b60201c565b61005757610056610e05565b5b61006f60016019600060016000610b9760201b60201c565b61007c5761007b610e05565b5b6100936001601e6001806000610b9760201b60201c565b6100a05761009f610e05565b5b6100d76001601b60017fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6000610b9760201b60201c565b6100e4576100e3610e05565b5b610152600160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476101169190610e6d565b600160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476101459190610e6d565b6003610bd260201b60201c565b61015f5761015e610e05565b5b610176600080600160026000610bd260201b60201c565b61018357610182610e05565b5b6101c5600060018060027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476101b89190610e6d565b6000610bd260201b60201c565b156101d3576101d2610e05565b5b6102146000806001807f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476102079190610ea1565b6000610bd260201b60201c565b1561022257610221610e05565b5b610283600160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476102549190610e6d565b610f007fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff610c0c60201b60201c565b6102905761028f610e05565b5b6102d0600160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476102c29190610e6d565b600080610c0c60201b60201c565b6102dd576102dc610e05565b5b6102f260008060036000610c0c60201b60201c565b6102ff576102fe610e05565b5b6103156000600460036000610c0c60201b60201c565b1561032357610322610e05565b5b61033860006004600080610c0c60201b60201c565b1561034657610345610e05565b5b60006040518060c001604052806001815260200160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476103879190610e6d565b81526020016000815260200160008152602001600081526020016000815250905060006040518060c001604052806001815260200160027f30644e72e131a029b85045b68181585d97816a916871ca8d3c208c16d87cfd476103e99190610e6d565b81526020017f1800deef121f1e76426a00665e5c4479674322d4f75edadd46debd5cd992f6ed81526020017f198e9393920d483a7260bfb731fb5d25f1aa493335a9e71297e485b7aef312c281526020017f12c85ea5db8c6deb4aab71808dcb408fe3d1e7690c43d37b4ce6cc0166fa7daa81526020017f090689d0585ff075ec9e99ad690c3395bc4b313370b38ef355acdadcd122975b815250905060006040518060c0016040528060008152602001600081526020017f1800deef121f1e76426a00665e5c4479674322d4f75edadd46debd5cd992f6ed81526020017f198e9393920d483a7260bfb731fb5d25f1aa493335a9e71297e485b7aef312c281526020017f12c85ea5db8c6deb4aab71808dcb408fe3d1e7690c43d37b4ce6cc0166fa7daa81526020017f090689d0585ff075ec9e99ad690c3395bc4b313370b38ef355acdadcd122975b815250905060006040518060c001604052806000815260200160008152602001600181526020017f198e9393920d483a7260bfb731fb5d25f1aa493335a9e71297e485b7aef312c281526020017f12c85ea5db8c6deb4aab71808dcb408fe3d1e7690c43d37b4ce6cc0166fa7daa81526020017f090689d0585ff075ec9e99ad690c3395bc4b313370b38ef355acdadcd122975b815250905060006040518060c0016040528060008152602001600c81526020017f1800deef121f1e76426a00665e5c4479674322d4f75edadd46debd5cd992f6ed81526020017f198e9393920d483a7260bfb731fb5d25f1aa493335a9e71297e485b7aef312c281526020017f12c85ea5db8c6deb4aab71808dcb408fe3d1e7690c43d37b4ce6cc0166fa7daa81526020017f090689d0585ff075ec9e99ad690c3395bc4b313370b38ef355acdadcd122975b815250905060008067ffffffffffffffff81111561069c5761069b610ed5565b5b6040519080825280602002602001820160405280156106d557816020015b6106c2610dcf565b8152602001906001900390816106ba5790505b5090506106e9816000610c4060201b60201c565b6106f6576106f5610e05565b5b600167ffffffffffffffff81111561071157610710610ed5565b5b60405190808252806020026020018201604052801561074a57816020015b610737610dcf565b81526020019060019003908161072f5790505b509050858160008151811061076257610761610f04565b5b602002602001018190525061077e816000610c4060201b60201c565b61078b5761078a610e05565b5b85816000815181106107a05761079f610f04565b5b6020026020010181905250600267ffffffffffffffff8111156107c6576107c5610ed5565b5b6040519080825280602002602001820160405280156107ff57816020015b6107ec610dcf565b8152602001906001900390816107e45790505b509050858160008151811061081757610816610f04565b5b6020026020010181905250848160018151811061083757610836610f04565b5b6020026020010181905250610853816000610c4060201b60201c565b6108605761085f610e05565b5b61087181600a610c4060201b60201c565b1561087f5761087e610e05565b5b828160018151811061089457610893610f04565b5b60200260200101819052506108b0816000610c4060201b60201c565b156108be576108bd610e05565b5b600367ffffffffffffffff8111156108d9576108d8610ed5565b5b60405190808252806020026020018201604052801561091257816020015b6108ff610dcf565b8152602001906001900390816108f75790505b509050858160008151811061092a57610929610f04565b5b6020026020010181905250848160018151811061094a57610949610f04565b5b6020026020010181905250838160028151811061096a57610969610f04565b5b6020026020010181905250610986816000610c4060201b60201c565b61099357610992610e05565b5b6109a4816001610c4060201b60201c565b156109b2576109b1610e05565b5b6109e2817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff610c4060201b60201c565b156109f0576109ef610e05565b5b8181600281518110610a0557610a04610f04565b5b6020026020010181905250610a21816000610c4060201b60201c565b15610a2f57610a2e610e05565b5b600a67ffffffffffffffff811115610a4a57610a49610ed5565b5b604051908082528060200260200182016040528015610a8357816020015b610a70610dcf565b815260200190600190039081610a685790505b50905060005b8151811015610b36576000600382610aa19190610f62565b03610aca5786828281518110610aba57610ab9610f04565b5b6020026020010181905250610b23565b6001600382610ad99190610f62565b03610b025785828281518110610af257610af1610f04565b5b6020026020010181905250610b22565b84828281518110610b1657610b15610f04565b5b60200260200101819052505b5b8080610b2e90610f93565b915050610a89565b50610b48816000610c4060201b60201c565b610b5557610b54610e05565b5b60006040518060400160405280600e81526020017f7a6b2d65766d206973206c6966650000000000000000000000000000000000008152509050805160208201f35b600060405186815285602082015284604082015283606082015260008084608001836001610bb8fa9150608081016040525095945050505050565b6000604051868152856020820152846040820152836060820152600080846080018360066096fa9150608081016040525095945050505050565b600060405185815284602082015283604082015260008084606001836007611770fa91506060810160405250949350505050565b60008061afc884516184d0610c559190610fdb565b610c5f9190610ea1565b90506000604051905060005b8551811015610d91576000868281518110610c8957610c88610f04565b5b60200260200101516000015190506000878381518110610cac57610cab610f04565b5b60200260200101516020015190506000888481518110610ccf57610cce610f04565b5b60200260200101516040015190506000898581518110610cf257610cf1610f04565b5b602002602001015160600151905060008a8681518110610d1557610d14610f04565b5b602002602001015160800151905060008b8781518110610d3857610d37610f04565b5b602002602001015160a0015190508660c00286818a015285602082018a015283604082018a015284606082018a015281608082018a01528260a082018a0152505050505050508080610d8990610f93565b915050610c6b565b50600060c08651610da29190610fdb565b905060008183610db29190610ea1565b905060008087840185600888fa9450806040525050505092915050565b6040518060c001604052806000815260200160008152602001600081526020016000815260200160008152602001600081525090565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b6000819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610e7882610e34565b9150610e8383610e34565b9250828203905081811115610e9b57610e9a610e3e565b5b92915050565b6000610eac82610e34565b9150610eb783610e34565b9250828201905080821115610ecf57610ece610e3e565b5b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b6000610f6d82610e34565b9150610f7883610e34565b925082610f8857610f87610f33565b5b828206905092915050565b6000610f9e82610e34565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610fd057610fcf610e3e565b5b600182019050919050565b6000610fe682610e34565b9150610ff183610e34565b9250828202610fff81610e34565b9150828204841483151761101657611015610e3e565b5b509291505056fe"))
        .run();
  }

  @Test
  void testEcRecoverWithEmptyExt() {
    BytecodeRunner.of(
            Bytes.fromHexString(
                "6080604052348015600f57600080fd5b5060476001601b6001620f00007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe609360201b60201c565b605157605060ce565b5b60006040518060400160405280600e81526020017f7a6b2d65766d206973206c6966650000000000000000000000000000000000008152509050805160208201f35b600060405186815285602082015284604082015283606082015260008084608001836001610bb8fa9150608081016040525095945050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fdfe"))
        .run();
  }
}