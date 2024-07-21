package org.gomspace.fundTransfer.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findByOwnerId_ValidOwnerId_Success() {
        OwnerId ownerId = new OwnerId(1);
        Account account = new Account(ownerId,new Currency("EUR"), BigDecimal.TEN);
        accountRepository.save(account);
        assertTrue(accountRepository.findByOwnerId(ownerId).isPresent());
    }
}
