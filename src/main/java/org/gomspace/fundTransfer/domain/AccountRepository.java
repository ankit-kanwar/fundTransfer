package org.gomspace.fundTransfer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, AccountId> {
    Optional<Account> findByOwnerId(OwnerId ownerId);
}
