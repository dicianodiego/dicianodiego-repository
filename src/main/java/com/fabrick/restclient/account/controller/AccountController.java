package com.fabrick.restclient.account.controller;

import com.fabrick.restclient.account.dto.MoneyTransfert;
import com.fabrick.restclient.account.dto.MoneyTransfertInput;
import com.fabrick.restclient.account.dto.Payload;
import com.fabrick.restclient.account.dto.Transaction;
import com.fabrick.restclient.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/balance/{accountId}")
    Payload findById(@PathVariable Long accountId) throws Exception
    {
        logger.info("retriving balance from account id " + accountId);
        return accountService.findAccountById(accountId).payload();
    }

    @GetMapping("/transactions/{accountId}")
    public List<Transaction> getTransations(@PathVariable Long accountId, @RequestParam String fromAccountingDate, @RequestParam String toAccountingDate) {
        logger.debug("retriving transactions from accountid: " + accountId +  ", fromAccountingDate:" + fromAccountingDate + ", toAccountingDate:" + toAccountingDate);
        return accountService.getTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @PostMapping("/moneyTransfert/{accountId}")
    public MoneyTransfert insertMoneyTranfert(@PathVariable Long accountId, @RequestBody MoneyTransfertInput moneyTransfertInput)
    {
        logger.info("[AccountController]<insertMoneyTransfert>inserting money transfert for " + accountId + "  of " + moneyTransfertInput.amount()  + " creditor " + moneyTransfertInput.creditor().name() );
        return accountService.insertMoneyTranfert(accountId, moneyTransfertInput);
    }




}
