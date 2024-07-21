package org.gomspace.fundTransfer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OwnerIdTest {
    @Test
    void ownerIdCreation_ValidId_Success() {
        OwnerId ownerId = new OwnerId(1);
        assertEquals(1, ownerId.id());
    }

    @Test
    void ownerIdCreation_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new OwnerId(null));
    }
}
