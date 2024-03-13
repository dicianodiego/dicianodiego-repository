package com.fabrick.restclient.account;

import com.fabrick.restclient.account.dto.*;
import com.fabrick.restclient.account.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(AccountService.class)
class AccountServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void shouldFindAccountBalance()  throws JsonProcessingException, Exception {
        final Long ACCOUNT_ID = Long.valueOf(14537780);
        Payload p = new Payload("2024-03-11", -35.57, -35.57, "EUR");
        Balance b = new Balance("OK", new String[0], p);
        String dataAsString = objectMapper.writeValueAsString(b);
        this.mockServer.expect(requestTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/14537780/balance"))
                .andRespond(withSuccess(dataAsString, MediaType.APPLICATION_JSON));

        Balance balance = accountService.findAccountById(ACCOUNT_ID);
        assertEquals(-35.57, balance.payload().availableBalance());
    }

    @Test
    void shouldFindTransaction() throws Exception{
        final Long ACCOUNT_ID = Long.valueOf(14537780);
        final String FROM_DATE = "2019-04-01";
        final String TO_DATE = "2019-04-10";


        this.mockServer.expect(requestTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"+ACCOUNT_ID+"/transactions?fromAccountingDate="+FROM_DATE+"&toAccountingDate="+TO_DATE))
                .andRespond(withSuccess());

        List<Transaction> l = accountService.getTransactions(ACCOUNT_ID, FROM_DATE, TO_DATE);
        logger.info("<trasactionTest> list transaction: " + l);
        assertEquals(Long.valueOf(1331714087),l.get(0).transactionId());
    }


    @Test
    void shouldFindExcptionOninsertMoneyTranfert() throws Exception{

        final Long ACCOUNT_ID = Long.valueOf(14537780);

        Account account = new Account("IT23A0336844430152923804660","SELBIT2BXXX");
        Address address = new Address("via roma,10","torino", "10100");
        Creditor creditor = new Creditor("John Doe", account, address );

        NaturalPersonBeneficiary naturalPersonBeneficiary = new NaturalPersonBeneficiary("MRLFNC81L04A859L",null,null,null,null);
        LegalPersonBeneficiary legalPersonBeneficiary = new LegalPersonBeneficiary("86334519757", null);
        TaxRelief taxRelief = new TaxRelief(
                "L449",false, "56258745832",
                "NATURAL_PERSON", naturalPersonBeneficiary, legalPersonBeneficiary);

        MoneyTransfertInput moneyTransfertInput = new MoneyTransfertInput(
                creditor, "2019-04-01", "REMITTANCE_INFORMATION",
                "Payment invoice 75/2017", Double.valueOf(800),  "EUR",
                false, false, "SHA",
                "45685475", taxRelief );


        String dataAsString = objectMapper.writeValueAsString(moneyTransfertInput);
        this.mockServer.expect(requestTo("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/14537780/payments/money-transfers"))
                .andRespond(withSuccess(dataAsString, MediaType.APPLICATION_JSON));

       RestClientException exception = Assertions.assertThrows(RestClientException.class, () -> accountService.insertMoneyTranfert(ACCOUNT_ID, moneyTransfertInput));
    }


}