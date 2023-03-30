package br.com.vr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @Size(min = 16, max = 16, message = "O campo 'numeroCartao' deve conter 16 dígitos")
    @NotBlank(message = "O campo 'numeroCartao' é obrigatório")
    @JsonProperty("numeroCartao")
    private String cardNumber;

    @NotBlank(message = "O campo 'senha' é obrigatório")
    @JsonProperty("senha")
    private String password;

    @NotNull(message = "O campo 'valor' é obrigatório")
    @JsonProperty("valor")
    private BigDecimal value;
}
