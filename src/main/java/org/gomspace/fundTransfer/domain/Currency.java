package org.gomspace.fundTransfer.domain;

import org.springframework.util.Assert;

public record Currency(String code) {
    public Currency {
        Assert.notNull(code, "Currency code is required");
        Assert.isTrue(!code.isEmpty(), "Currency code must not be empty");
    }

}
