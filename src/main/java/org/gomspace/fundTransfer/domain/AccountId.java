package org.gomspace.fundTransfer.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record AccountId(UUID id) {
    public AccountId{
        Assert.notNull(id, "id must not be null");
    }

    public AccountId() {
        this(UUID.randomUUID());
    }
}
