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
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by mroon on 18.05.17.
 */
public class MySmartContractTest {
    EthAccount account1 = AccountProvider.fromSeed("titi1");
    EthAccount account2 = AccountProvider.fromSeed("titi2");

    EthereumFacade ethereumFacade = EthjEthereumFacadeProvider
            .forTest(TestConfig.builder().balance(account1, EthValue.ether(100)).build());

    @Test
    public void testToken() throws ExecutionException, InterruptedException {
        CompilationResult compilationResult = ethereumFacade.compile(SoliditySourceFile.from(new File("src/contracts/Token.sol")));
        SolidityContractDetails tokenDetails = compilationResult.findContract("Token").get();
        EthAddress contractAddress = ethereumFacade
                .publishContract(tokenDetails, account1, Long.valueOf(1000000), Long.valueOf(1000)).get();

        IToken token = ethereumFacade.createContractProxy(tokenDetails, contractAddress, account1, IToken.class);
        assertEquals(1000000, token.supply().longValue());
        assertEquals(100000, token.balances(account1.getAddress()).longValue());

        Boolean res = token.transfer(account2.getAddress(), 10000L).get();
        assertTrue(res);
        assertEquals(10000, token.balances(account2.getAddress()).longValue());

        token.buy().with(EthValue.wei(100000000)).get();
        assertEquals(200000, token.balances(account1.getAddress()).longValue());
    }
}
