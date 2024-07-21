package org.gomspace.fundTransfer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyTest {
    @Test
    void currencyCreation_ValidCode_Success() {
        Currency currency = new Currency("USD");
        assertEquals("USD", currency.code());
    }

    @Test
    void currencyCreation_EmptyCode_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Currency(""));
    }
}
