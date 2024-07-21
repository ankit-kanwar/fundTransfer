package org.gomspace.fundTransfer.application;

import java.math.BigDecimal;

public record TransferRequestDTO(Integer sourceAccountOwnerId, Integer targetAccountOwnerId,
                                 String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
}
