package org.gomspace.fundTransfer.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountIdTest {
    @Test
    void accountIdCreation_ValidData_Success() {

        AccountId accountId = new AccountId();
        assertNotNull(accountId);
    }
}
