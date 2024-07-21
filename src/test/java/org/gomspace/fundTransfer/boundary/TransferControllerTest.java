package org.gomspace.fundTransfer.boundary;

import org.gomspace.fundTransfer.application.TransferUseCase;
import org.gomspace.fundTransfer.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferUseCase transferUseCase;

    @Test
    void transfer_ValidRequest_ReturnsOk() throws Exception {
        mockMvc.perform(post("/transfer")
                        .contentType("application/json")
                        .content("{\"sourceAccountOwnerId\":1,\"targetAccountOwnerId\":2,\"sourceCurrencyCode\":\"USD\",\"targetCurrencyCode\":\"EUR\",\"amount\":100}"))
                .andExpect(status().isOk());
    }

    @Test
    void transfer_InsufficientBalance_Returns400() throws Exception {
        doThrow(new InsufficientBalanceException("Insufficient balance")).when(transferUseCase).transfer(any());
        mockMvc.perform(post("/transfer")
                        .contentType("application/json")
                        .content("{\"sourceAccountOwnerId\":1,\"targetAccountOwnerId\":2,\"sourceCurrencyCode\":\"USD\",\"targetCurrencyCode\":\"EUR\",\"amount\":100}"))
                .andExpect(status().is(400));
    }

}
