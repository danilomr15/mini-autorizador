package br.com.vr.service;

import br.com.vr.dto.TransactionDTO;
import br.com.vr.enums.TransactionStatusEnum;
import br.com.vr.exception.TransactionException;
import br.com.vr.producer.TransactionMessageProducer;
import br.com.vr.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepository;
    private final TransactionMessageProducer transactionMessageProducer;

    public String newTransaction(final TransactionDTO transactionDTO) {

        final var card = cardRepository.findById(transactionDTO.getCardNumber())
            .orElseThrow(() -> new TransactionException(TransactionStatusEnum.CARD_NOT_FOUND));

        card.validPassword(transactionDTO.getPassword())
            .filter(Boolean.TRUE::equals)
            .orElseThrow(() -> new TransactionException(TransactionStatusEnum.INVALID_PASSWORD));

        card.hasFunds(transactionDTO.getValue())
            .filter(Boolean.TRUE::equals)
            .orElseThrow(() -> new TransactionException(TransactionStatusEnum.INSUFFICIENT_FUNDS));

        card.setBalance(card.getBalance().subtract(transactionDTO.getValue()));
        transactionMessageProducer.send(transactionDTO);

        return TransactionStatusEnum.OK.getDescription();
    }
}
