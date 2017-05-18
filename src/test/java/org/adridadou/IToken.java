package org.adridadou;

import org.adridadou.ethereum.propeller.values.EthAddress;
import org.adridadou.ethereum.propeller.values.EthValue;
import org.adridadou.ethereum.propeller.values.Payable;

import java.util.concurrent.CompletableFuture;

/**
 * Created by mroon on 18.05.17.
 */
public interface IToken {

    Long balances(EthAddress addr);
    Long price();
    Long supply();
    EthAddress owner();

    Payable buy();

    CompletableFuture<Boolean> transfer(EthAddress to, Long amount);

    CompletableFuture payout();
}
