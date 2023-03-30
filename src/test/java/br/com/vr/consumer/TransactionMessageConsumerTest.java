package br.com.vr.consumer;

import br.com.vr.domain.Card;
import br.com.vr.exception.TransactionException;
import br.com.vr.fixture.CardFixture;
import br.com.vr.fixture.TransactionFixture;
import br.com.vr.repository.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionMessageConsumerTest {

    @InjectMocks
    private TransactionMessageConsumer transactionMessageConsumer;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private CardRepository cardRepository;

    @Test
    @DisplayName("SUCESSO - Receber mensagem de transação e salvar no banco")
    void test1() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));
        when(cardRepository.save(any(Card.class))).thenReturn(new Card());

        transactionMessageConsumer.consume(TransactionFixture.jsonTransaction());

        verify(cardRepository, times(1)).findById("1236547896541235");
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("SUCESSO - Receber mensagem de transação e salvar no banco")
    void test2() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.empty());

        try {
            transactionMessageConsumer.consume(TransactionFixture.jsonTransaction());
            fail();
        } catch (TransactionException ex) {
            verify(cardRepository, times(1)).findById("1236547896541235");
            verify(cardRepository, never()).save(any(Card.class));
        } catch (Exception ex) {
            fail();
        }
    }
}
