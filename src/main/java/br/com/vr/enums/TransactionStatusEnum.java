package br.com.vr.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TransactionStatusEnum {

    OK("OK"),
    INSUFFICIENT_FUNDS("SALDO_INSUFICIENTE"),
    INVALID_PASSWORD("SENHA_INVALIDA"),
    CARD_NOT_FOUND("CARTAO_INEXISTENTE");

    private String description;
}
