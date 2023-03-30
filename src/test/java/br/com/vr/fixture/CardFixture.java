package br.com.vr.fixture;

import br.com.vr.domain.Card;
import br.com.vr.dto.CardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class CardFixture {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Card defaultCard() {

        return Card.builder()
            .password("1234")
            .balance(BigDecimal.valueOf(431.52))
            .cardNumber("1236547896541235")
            .build();
    }

    public static CardDTO defaultCardDTO() {

        return CardDTO.builder()
            .password("1234")
            .cardNumber("1236547896541235")
            .build();
    }
}
