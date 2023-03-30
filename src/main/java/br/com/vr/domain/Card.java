package br.com.vr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private BigDecimal balance;

    public Optional<Boolean> validPassword(final String password) {

        return Optional.of(this.password.equals(password));
    }

    public Optional<Boolean> hasFunds(final BigDecimal value) {

        return Optional.of(this.balance.compareTo(value) >= 0);
    }
}
