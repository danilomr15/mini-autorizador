package br.com.vr.consumer;

import br.com.vr.dto.TransactionDTO;
import br.com.vr.enums.TransactionStatusEnum;
import br.com.vr.exception.MessagingException;
import br.com.vr.exception.TransactionException;
import br.com.vr.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMessageConsumer {

    private final ObjectMapper objectMapper;
    private final CardRepository cardRepository;

    @RabbitListener(queues = {"${rabbitmq.queue-name}"})
    public void consume(@Payload final String message) {

        final var transactionDTO = toObject(message);
        final var card = cardRepository.findById(transactionDTO.getCardNumber())
            .orElseThrow(() -> new TransactionException(TransactionStatusEnum.CARD_NOT_FOUND));

        card.setBalance(card.getBalance().subtract(transactionDTO.getValue()));
        cardRepository.save(card);
    }

    private TransactionDTO toObject(final String message) {

        try {
            return objectMapper.readValue(message, TransactionDTO.class);
        } catch (JsonProcessingException e) {
            throw new MessagingException();
        }
    }
}
