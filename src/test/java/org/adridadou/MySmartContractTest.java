package org.adridadou;

import org.adridadou.ethereum.EthjEthereumFacadeProvider;
import org.adridadou.ethereum.ethj.TestConfig;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.adridadou.ethereum.propeller.keystore.AccountProvider;
import org.adridadou.ethereum.propeller.solidity.CompilationResult;
import org.adridadou.ethereum.propeller.solidity.SolidityContractDetails;
import org.adridadou.ethereum.propeller.values.EthAccount;
import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.EthValue;
import org.adridadou.ethereum.propeller.values.SoliditySourceFile;
import org.ethereum.jsonrpc.JsonRpc;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by mroon on 18.05.17.
 */
public class MySmartContractTest {
    EthAccount account1 = AccountProvider.fromSeed("titi1");
    EthAccount account2 = AccountProvider.fromSeed("titi2");

    EthereumFacade ethereumFacade = EthjEthereumFacadeProvider
            .forTest(TestConfig.builder().balance(account1, EthValue.ether(100)).build());

    @Test
    public void testMetaCoin() throws ExecutionException, InterruptedException {
        CompilationResult compilationResult = ethereumFacade.compile(SoliditySourceFile.from(new File("truffle/contracts/MetaCoin.sol")));
        SolidityContractDetails metaCoinResult = compilationResult.findContract("MetaCoin").get();
        EthAddress contractAddress = ethereumFacade.publishContract(metaCoinResult, account1).get();

        IMetaCoin metaCoin = ethereumFacade.createContractProxy(metaCoinResult, contractAddress, account1, IMetaCoin.class);
    }
}
