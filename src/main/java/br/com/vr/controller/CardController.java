package br.com.vr.controller;

import br.com.vr.adapter.CardAdapter;
import br.com.vr.dto.CardDTO;
import br.com.vr.service.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/cartoes")
public class CardController {

    private final CardService cardService;
    private final CardAdapter cardAdapter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDTO create(@RequestBody @Valid final CardDTO cardDTO) {

        final var newCard = cardAdapter.adapt(cardDTO);
        return cardAdapter.adapt(cardService.create(newCard));
    }

    @GetMapping("/{cardNumber}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCardBalance(@PathVariable("cardNumber") final String cardNumber) {

        return cardService.getCardBalance(cardNumber);
    }
}
