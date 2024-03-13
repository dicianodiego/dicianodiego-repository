package com.fabrick.restclient.account;

public record TaxRelief(String taxReliefId, Boolean isCondoUpgrade, String creditorFiscalCode,
                        String beneficiaryType, NaturalPersonBeneficiary naturalPersonBeneficiary,
                        LegalPersonBeneficiary legalPersonBeneficiary) {
}
