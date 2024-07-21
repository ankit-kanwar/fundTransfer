package org.gomspace.fundTransfer.domain;

import org.springframework.util.Assert;

public record OwnerId(Integer id) {
    public OwnerId {
        Assert.notNull(id, "id must not be null");
    }
}
