package com.fabrick.restclient.account.service;

import com.fabrick.restclient.account.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class AccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestClient restClient;

    public AccountService(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://sandbox.platfr.io")
                .build();
    }

    public Balance findAccountById(Long accountId) throws Exception{
        return restClient.get()
                .uri("/api/gbs/banking/v4.0/accounts/{accountId}/balance", accountId)
                .header("Auth-Schema", "S2S")
                .header("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP")
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    logger.error("ERROR 500 occured!!!");
                    throw new ErrorBalanceException("BALANCE ERROR");
                })
                .body(Balance.class);
    }

    public List<Transaction> getTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
        return restClient.get()
                .uri("/api/gbs/banking/v4.0/accounts/{accountId}/transactions?fromAccountingDate={fromAccountingDate}&toAccountingDate={toAccountingDate}", accountId,fromAccountingDate,toAccountingDate)
                .header("Auth-Schema", "S2S")
                .header("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP")
                .retrieve()
                .onStatus(status -> status.value() == 400, (request, response) -> {
                    logger.error("ERROR 400 occured!!!");
                    throw new ErrorTransactionException("TRANSACTION ERROR");
                })
                .body(new ParameterizedTypeReference<List<Transaction>>(){});
    }

    public MoneyTransfert insertMoneyTranfert(Long accountId, MoneyTransfertInput moneyTransfertInput)  {
        logger.info("[AccountService]<insertMoneyTranfert>inserting money transfert for " + accountId + "  of " + moneyTransfertInput.amount()  + " creditor " + moneyTransfertInput.creditor().name() );
        final String API_MONEY_TRANSFERT = "/api/gbs/banking/v4.0/accounts/"+accountId+"/payments/money-transfers";
        return restClient.post()
                .uri(API_MONEY_TRANSFERT)
                .header("Auth-Schema", "S2S")
                .header("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP")
                .header("X-Time-Zone", "")
                .contentType(MediaType.APPLICATION_JSON)
                .body(moneyTransfertInput)
                .retrieve()
                .onStatus(status -> status.value() == 400, (request, response) -> {
                    logger.error("ERROR 400 occured!!!");
                         throw new ErrorAccountException("ACCOUNT ERROR");
                })
                .body(MoneyTransfert.class);
    }
}
