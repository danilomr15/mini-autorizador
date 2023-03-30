package br.com.vr.producer;

import br.com.vr.fixture.TransactionFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionMessageProducerTest {

    @InjectMocks
    private TransactionMessageProducer transactionMessageProducer;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("SUCESSO - Envio de mensagem de transação")
    void test1() {

        doNothing().when(rabbitTemplate).convertAndSend(eq("exchange_test"), eq("routing_key_test"), anyString());

        ReflectionTestUtils.setField(transactionMessageProducer, "exchangeName", "exchange_test");
        ReflectionTestUtils.setField(transactionMessageProducer, "routingKeyName", "routing_key_test");

        transactionMessageProducer.send(TransactionFixture.defaultTransaction());

        verify(rabbitTemplate, times(1)).convertAndSend(eq("exchange_test"), eq("routing_key_test"), anyString());
    }

}
