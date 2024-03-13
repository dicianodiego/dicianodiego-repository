package com.fabrick.restclient.account.dto;

public record TaxRelief(String taxReliefId, Boolean isCondoUpgrade, String creditorFiscalCode,
                        String beneficiaryType, NaturalPersonBeneficiary naturalPersonBeneficiary,
                        LegalPersonBeneficiary legalPersonBeneficiary) {
}
