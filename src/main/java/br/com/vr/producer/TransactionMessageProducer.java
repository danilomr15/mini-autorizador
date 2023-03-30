package br.com.vr.producer;

import br.com.vr.dto.TransactionDTO;
import br.com.vr.exception.MessagingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key-name}")
    private String routingKeyName;

    public void send(final TransactionDTO transactionDTO) {

        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, toJson(transactionDTO));
    }

    private String toJson(final TransactionDTO transactionDTO) {

        try {
            return objectMapper.writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(transactionDTO);
        } catch (JsonProcessingException e) {
            throw new MessagingException();
        }
    }
}
