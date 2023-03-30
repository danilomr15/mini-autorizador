package br.com.vr.adapter;

import br.com.vr.domain.Card;
import br.com.vr.dto.CardDTO;
import org.springframework.stereotype.Component;

@Component
public class CardAdapter {

    public CardDTO adapt(final Card card) {

        return CardDTO.builder()
            .cardNumber(card.getCardNumber())
            .password(card.getPassword())
            .build();
    }

    public Card adapt(final CardDTO card) {

        return Card.builder()
            .cardNumber(card.getCardNumber())
            .password(card.getPassword())
            .build();
    }
}
