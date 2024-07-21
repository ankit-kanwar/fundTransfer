package org.gomspace.fundTransfer.application;

import org.gomspace.fundTransfer.domain.Currency;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeService {
    Optional<BigDecimal> getExchangeRate(Currency sourceCurrency, Currency targetCurrency);
}
