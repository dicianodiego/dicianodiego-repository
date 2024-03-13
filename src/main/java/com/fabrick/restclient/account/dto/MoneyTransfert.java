package com.fabrick.restclient.account.dto;

import java.util.Date;
import java.util.List;

public record MoneyTransfert(Long moneyTransferId, String status, String direction,
                             Creditor creditor, Debtor debtor, String cro,
                             String uri, String trn, String description,
                             Date createdDatetime, Date accountedDatetime, String debtorValueDate,
                             String creditorValueDate, Amount amount, Boolean isUrgent,
                             Boolean isInstant, Long feeAccountId, List<Fees> fees,
                             Boolean hasTaxRelief)
{
}
