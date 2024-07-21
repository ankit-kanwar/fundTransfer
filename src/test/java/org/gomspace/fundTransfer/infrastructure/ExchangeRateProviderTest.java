package org.gomspace.fundTransfer.infrastructure;

import org.gomspace.fundTransfer.domain.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateProviderTest {
    @InjectMocks
    private ExchangeRateProvider exchangeRateProvider;

    @Test
    void getExchangeRate_USDToEUR_ReturnsCorrectRate() {
        Optional<BigDecimal> rate = exchangeRateProvider.getExchangeRate(new Currency("USD"), new Currency("EUR"));
        assertTrue(rate.isPresent());
        assertEquals(0.85, rate.get().doubleValue());
    }
}
