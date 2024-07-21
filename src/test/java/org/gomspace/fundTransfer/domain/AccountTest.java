package org.gomspace.fundTransfer.domain;

import org.gomspace.fundTransfer.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void accountCreation_ValidData_Success() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertEquals("EUR", account.getCurrency().code());
        assertEquals(BigDecimal.TEN, account.getBalance());
    }

    @Test
    void debit_SufficientBalance_Success() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), new BigDecimal("100"));
        account.debit(new BigDecimal("50"));
        assertEquals(new BigDecimal("50"), account.getBalance());
    }

    @Test
    void credit_ValidAmount_Success() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.ZERO);
        account.credit(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), account.getBalance());
    }

    @Test
    void accountCreation_NullOwnerId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Account(null, new Currency("EUR"), BigDecimal.TEN));
    }

    @Test
    void debit_InsufficientBalance_ThrowsException() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertThrows(InsufficientBalanceException.class, () -> account.debit(new BigDecimal("20")));
    }

    @Test
    void debit_NullAmount_ThrowsException() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertThrows(NullPointerException.class, () -> account.debit(null));
    }

    @Test
    void debit_NegativeAmount_ThrowsException() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> account.debit(new BigDecimal("-1")));
    }

    @Test
    void credit_NullAmount_ThrowsException() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertThrows(NullPointerException.class, () -> account.credit(null));
    }

    @Test
    void credit_NegativeAmount_ThrowsException() {
        Account account = new Account(new OwnerId(1), new Currency("EUR"), BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> account.credit(new BigDecimal("-1")));
    }

}

