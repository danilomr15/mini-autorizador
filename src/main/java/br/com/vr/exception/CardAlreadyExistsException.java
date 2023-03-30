package br.com.vr.exception;

import br.com.vr.domain.Card;
import br.com.vr.dto.CardDTO;
import lombok.Getter;

@Getter
public class CardAlreadyExistsException extends RuntimeException {

    private final CardDTO cardDTO;

    public CardAlreadyExistsException(final Card card) {

        this.cardDTO = CardDTO.builder()
            .password(card.getPassword())
            .cardNumber(card.getCardNumber())
            .build();
    }
}
