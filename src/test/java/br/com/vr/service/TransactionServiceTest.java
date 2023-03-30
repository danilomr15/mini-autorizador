package br.com.vr.service;

import br.com.vr.dto.TransactionDTO;
import br.com.vr.enums.TransactionStatusEnum;
import br.com.vr.exception.TransactionException;
import br.com.vr.fixture.CardFixture;
import br.com.vr.fixture.TransactionFixture;
import br.com.vr.producer.TransactionMessageProducer;
import br.com.vr.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionMessageProducer transactionMessageProducer;

    @Test
    @DisplayName("SUCESSO - Lançamento de Transação")
    void test1() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));
        doNothing().when(transactionMessageProducer).send(any(TransactionDTO.class));

        final var result = transactionService.newTransaction(TransactionFixture.defaultTransaction());
        assertNotNull(result);
        assertEquals("OK", result);

        verify(cardRepository, times(1)).findById("1236547896541235");
        verify(transactionMessageProducer, times(1)).send(any(TransactionDTO.class));
    }

    @Test
    @DisplayName("ERRO - Cartão não encontrado")
    void test2() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.empty());

        try {
            transactionService.newTransaction(TransactionFixture.defaultTransaction());
            fail();
        } catch (TransactionException ex) {

            assertEquals(TransactionStatusEnum.CARD_NOT_FOUND, ex.getStatus());

            verify(cardRepository, times(1)).findById("1236547896541235");
            verify(transactionMessageProducer, never()).send(any(TransactionDTO.class));
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    @DisplayName("ERRO - Senha incorreta")
    void test3() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));

        try {
            transactionService.newTransaction(TransactionFixture.withPassword("4321"));
            fail();
        } catch (TransactionException ex) {

            assertEquals(TransactionStatusEnum.INVALID_PASSWORD, ex.getStatus());

            verify(cardRepository, times(1)).findById("1236547896541235");
            verify(transactionMessageProducer, never()).send(any(TransactionDTO.class));
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    @DisplayName("ERRO - Saldo insuficiente")
    void test4() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));

        try {
            transactionService.newTransaction(TransactionFixture.withValue(BigDecimal.valueOf(1500.0)));
            fail();
        } catch (TransactionException ex) {

            assertEquals(TransactionStatusEnum.INSUFFICIENT_FUNDS, ex.getStatus());

            verify(cardRepository, times(1)).findById("1236547896541235");
            verify(transactionMessageProducer, never()).send(any(TransactionDTO.class));
        } catch (Exception ex) {
            fail();
        }
    }
}
