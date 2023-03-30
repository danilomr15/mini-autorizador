package br.com.vr.service;

import br.com.vr.domain.Card;
import br.com.vr.exception.CardAlreadyExistsException;
import br.com.vr.exception.CardNotFoundException;
import br.com.vr.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card create(final Card card) {

        cardRepository.findById(card.getCardNumber())
            .ifPresent(c -> {
                throw new CardAlreadyExistsException(card);
            });

        card.setBalance(BigDecimal.valueOf(500));
        return cardRepository.save(card);
    }

    public BigDecimal getCardBalance(final String cardNumber) {

        return cardRepository.findById(cardNumber)
            .map(Card::getBalance)
            .orElseThrow(CardNotFoundException::new);
    }
}
