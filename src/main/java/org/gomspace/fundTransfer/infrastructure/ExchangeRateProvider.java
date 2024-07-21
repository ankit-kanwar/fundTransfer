package org.gomspace.fundTransfer.infrastructure;

import org.gomspace.fundTransfer.application.ExchangeService;
import org.gomspace.fundTransfer.domain.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ExchangeRateProvider implements ExchangeService {
    @Override
    public Optional<BigDecimal> getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        return Optional.ofNullable(
                switch (sourceCurrency.code()) {
                    case "USD" -> switch (targetCurrency.code()) {
                        case "EUR" -> BigDecimal.valueOf(0.85);
                        case "GBP" -> BigDecimal.valueOf(0.75);
                        default -> null;
                    };
                    case "EUR" -> switch (targetCurrency.code()) {
                        case "USD" -> BigDecimal.valueOf(1.18);
                        case "GBP" -> BigDecimal.valueOf(0.88);
                        default -> null;
                    };
                    case "GBP" -> switch (targetCurrency.code()) {
                        case "USD" -> BigDecimal.valueOf(1.33);
                        case "EUR" -> BigDecimal.valueOf(1.14);
                        default -> null;
                    };
                    default -> null;
                }
        );
    }
}
