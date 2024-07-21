package org.gomspace.fundTransfer.domain;

import java.math.BigDecimal;

public record ExchangeRate(Currency sourceCurrency,
                           Currency targetCurrency,
                           BigDecimal rate) {

}
