package org.gomspace.fundTransfer.domain;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.gomspace.fundTransfer.domain.exception.InsufficientBalanceException;
import org.springframework.util.Assert;


import java.math.BigDecimal;

@Entity
@Getter
@Slf4j
public class Account {

    @EmbeddedId
    private AccountId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private OwnerId ownerId;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "currency"))
    private Currency currency;

    private BigDecimal balance;

    @Version
    private Long version;

    Account(){
    }

    public Account(OwnerId ownerId, Currency currency, BigDecimal balance) {
        Assert.notNull(ownerId, "ownerId must not be null");
        this.id = new AccountId();
        this.ownerId = ownerId;
        this.currency = currency;
        this.balance = balance;
    }

    public void debit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Trying to debit negative amount for account owner id: {}", id);
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            log.error("Insufficient balance for account owner id: {}", id);
            throw new InsufficientBalanceException("Insufficient balance for source account owner id: " + id);
        }
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Trying to credit negative amount for account owner id: {}", id);
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
    }
}
