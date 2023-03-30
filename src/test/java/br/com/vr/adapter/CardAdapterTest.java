package br.com.vr.adapter;

import br.com.vr.fixture.CardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CardAdapterTest {

    @InjectMocks
    private CardAdapter cardAdapter;

    @Test
    @DisplayName("SUCESSO - Converter Card para CardDTO")
    void test1() {

        final var card = CardFixture.defaultCard();
        final var cardDTO = cardAdapter.adapt(card);
        assertNotNull(cardDTO);
        assertEquals(cardDTO.getCardNumber(), card.getCardNumber());
        assertEquals(cardDTO.getPassword(), card.getPassword());
    }

    @Test
    @DisplayName("SUCESSO - Converter CardDTO para Card")
    void test2() {

        final var cardDTO = CardFixture.defaultCardDTO();
        final var card = cardAdapter.adapt(cardDTO);
        assertNotNull(card);
        assertEquals(cardDTO.getCardNumber(), card.getCardNumber());
        assertEquals(cardDTO.getPassword(), card.getPassword());
    }
}
