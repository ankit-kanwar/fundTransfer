package org.gomspace.fundTransfer.application;

import org.gomspace.fundTransfer.domain.OwnerId;

import java.math.BigDecimal;

public record TransferCommand(
        OwnerId sourceOwnerId,
        OwnerId targetOwnerId,
        BigDecimal amount
) {}
