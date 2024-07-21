package org.gomspace.fundTransfer.boundary;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gomspace.fundTransfer.application.TransferCommand;
import org.gomspace.fundTransfer.application.TransferRequestDTO;
import org.gomspace.fundTransfer.application.TransferUseCase;
import org.gomspace.fundTransfer.domain.OwnerId;
import org.gomspace.fundTransfer.domain.exception.AccountNotFoundException;
import org.gomspace.fundTransfer.domain.exception.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@AllArgsConstructor
@Slf4j
public class TransferController {
    private final TransferUseCase transferUseCase;

    @PostMapping
    public ResponseEntity transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {

            transferUseCase.transfer( new TransferCommand(new OwnerId(transferRequestDTO.sourceAccountOwnerId()),
                                                          new OwnerId(transferRequestDTO.targetAccountOwnerId()),
                                                          transferRequestDTO.amount() ) );
            log.info("Fund transfer is successful from account owner: {} to account owner: {} for amount: {}",
                    transferRequestDTO.sourceAccountOwnerId(), transferRequestDTO.targetAccountOwnerId(), transferRequestDTO.amount());
            return ResponseEntity.ok().body("Fund transfer is successful");
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            log.error("Error while transferring fund", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while transferring fund", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
