package org.adridadou;

import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.EthValue;

import java.util.concurrent.CompletableFuture;

/**
 * Created by mroon on 18.05.17.
 */
public interface IMetaCoin {

    EthValue getBalanceInEth(EthAddress addr);

    CompletableFuture<Boolean> sendCoin(EthAddress receiver, Long amount);

    Long getBalance(EthAddress addr);

}
