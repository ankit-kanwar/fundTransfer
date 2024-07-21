package org.gomspace.fundTransfer.application;

import org.gomspace.fundTransfer.application.exception.ExchangeRateNotFoundException;
import org.gomspace.fundTransfer.domain.Account;
import org.gomspace.fundTransfer.domain.AccountRepository;
import org.gomspace.fundTransfer.domain.Currency;
import org.gomspace.fundTransfer.domain.OwnerId;
import org.gomspace.fundTransfer.domain.exception.AccountNotFoundException;
import org.gomspace.fundTransfer.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransferUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private TransferUseCase transferUseCase;

    private OwnerId sourceOwnerId;
    private OwnerId targetOwnerId;
    private Currency sourceCurrency;
    private Currency targetCurrency;

    @BeforeEach
    void setup() {
         sourceOwnerId = new OwnerId(1);
         targetOwnerId = new OwnerId(2);
         sourceCurrency = new Currency("USD");
         targetCurrency = new Currency("EUR");
    }

    @Test
    void transfer_ValidCommand_Success() throws Exception {
        TransferCommand command = new TransferCommand(sourceOwnerId, targetOwnerId, new BigDecimal("10.00"));
        Account sourceAccount = new Account(sourceOwnerId, sourceCurrency, new BigDecimal("100.00"));
        Account targetAccount = new Account(targetOwnerId, targetCurrency, new BigDecimal("100.00"));
        BigDecimal exchangeRate = new BigDecimal("0.85");

        when(accountRepository.findByOwnerId(sourceOwnerId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByOwnerId(targetOwnerId)).thenReturn(Optional.of(targetAccount));
        when(exchangeService.getExchangeRate(sourceCurrency, targetCurrency)).thenReturn(Optional.of(exchangeRate));

        transferUseCase.transfer(command);

        verify(accountRepository).save(sourceAccount);
        verify(accountRepository).save(targetAccount);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void transfer_SourceAccountNotFound_ThrowsException() {
        TransferCommand command = new TransferCommand(sourceOwnerId, targetOwnerId, new BigDecimal("10.00"));

        when(accountRepository.findByOwnerId(sourceOwnerId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> transferUseCase.transfer(command));
    }

    @Test
    public void transfer_TargetAccountNotFound_ThrowsException() {
        TransferCommand command = new TransferCommand(sourceOwnerId, targetOwnerId, new BigDecimal("10.00"));
        Account sourceAccount = new Account(sourceOwnerId, sourceCurrency, new BigDecimal("100.00"));

        when(accountRepository.findByOwnerId(sourceOwnerId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByOwnerId(targetOwnerId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> transferUseCase.transfer(command));
    }

    @Test
    public void transfer_ExchangeRateNotFound_ThrowsException() {
        TransferCommand command = new TransferCommand(sourceOwnerId, targetOwnerId, new BigDecimal("10.00"));
        Account sourceAccount = new Account(sourceOwnerId, sourceCurrency, new BigDecimal("100.00"));
        Account targetAccount = new Account(targetOwnerId, targetCurrency, new BigDecimal("100.00"));

        when(accountRepository.findByOwnerId(sourceOwnerId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByOwnerId(targetOwnerId)).thenReturn(Optional.of(targetAccount));
        when(exchangeService.getExchangeRate(sourceCurrency, targetCurrency)).thenReturn(Optional.empty());

        assertThrows(ExchangeRateNotFoundException.class, () -> transferUseCase.transfer(command));
    }

    @Test
    void transfer_InsufficientBalance_ThrowsException() {
        TransferCommand command = new TransferCommand(sourceOwnerId, targetOwnerId, new BigDecimal("100.00"));
        Account sourceAccount = new Account(sourceOwnerId, sourceCurrency, new BigDecimal("10.00"));
        Account targetAccount = new Account(targetOwnerId, targetCurrency, new BigDecimal("100.00"));
        BigDecimal exchangeRate = new BigDecimal("0.85");

        when(accountRepository.findByOwnerId(sourceOwnerId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByOwnerId(targetOwnerId)).thenReturn(Optional.of(targetAccount));
        when(exchangeService.getExchangeRate(sourceCurrency, targetCurrency)).thenReturn(Optional.of(exchangeRate));


        assertThrows(InsufficientBalanceException.class, () -> transferUseCase.transfer(command));
    }

}
