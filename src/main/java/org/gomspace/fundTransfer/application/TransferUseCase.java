package org.gomspace.fundTransfer.application;

import lombok.AllArgsConstructor;
import org.gomspace.fundTransfer.UseCase;
import org.gomspace.fundTransfer.application.exception.ExchangeRateNotFoundException;
import org.gomspace.fundTransfer.domain.Account;
import org.gomspace.fundTransfer.domain.AccountRepository;
import org.gomspace.fundTransfer.domain.exception.AccountNotFoundException;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class TransferUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeService exchangeService;


    public void transfer(TransferCommand command) {
        Account sourceAccount = accountRepository.findByOwnerId(command.sourceOwnerId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for source account owner: " + command.sourceOwnerId()));
        Account targetAccount = accountRepository.findByOwnerId(command.targetOwnerId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for target account owner: " + command.targetOwnerId()));

        BigDecimal exchangeRate = exchangeService.getExchangeRate(sourceAccount.getCurrency(), targetAccount.getCurrency())
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found for: " + sourceAccount.getCurrency() + " -> " + targetAccount.getCurrency()));

        BigDecimal amountInSourceCurrency = command.amount();
        BigDecimal amountInTargetCurrency = amountInSourceCurrency.multiply(exchangeRate);

        sourceAccount.debit(amountInSourceCurrency);
        targetAccount.credit(amountInTargetCurrency);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
    }

}
