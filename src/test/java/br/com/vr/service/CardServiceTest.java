package br.com.vr.service;

import br.com.vr.domain.Card;
import br.com.vr.exception.CardAlreadyExistsException;
import br.com.vr.exception.CardNotFoundException;
import br.com.vr.fixture.CardFixture;
import br.com.vr.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Test
    @DisplayName("SUCESSO - Criar novo Cartão")
    void test1() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.empty());
        when(cardRepository.save(any(Card.class))).thenReturn(new Card());

        final var card = cardService.create(CardFixture.defaultCard());
        assertNotNull(card);

        verify(cardRepository, times(1)).findById("1236547896541235");
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("ERRO - Cartão existente")
    void test2() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));

        try {
            cardService.create(CardFixture.defaultCard());
            fail();
        } catch (CardAlreadyExistsException ex) {
            verify(cardRepository, times(1)).findById("1236547896541235");
            verify(cardRepository, never()).save(any(Card.class));
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    @DisplayName("SUCESSO - Consultar saldo de Cartão")
    void test3() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.of(CardFixture.defaultCard()));

        final var balance = cardService.getCardBalance("1236547896541235");
        assertNotNull(balance);
        assertEquals(0, balance.compareTo(BigDecimal.valueOf(431.52)));

        verify(cardRepository, times(1)).findById("1236547896541235");
    }

    @Test
    @DisplayName("ERRO - Cartão não encontrado")
    void test4() {

        when(cardRepository.findById("1236547896541235")).thenReturn(Optional.empty());

        try {
            cardService.getCardBalance("1236547896541235");
            fail();
        } catch (CardNotFoundException ex) {
            verify(cardRepository, times(1)).findById("1236547896541235");
        } catch (Exception ex) {
            fail();
        }
    }
}
