package br.com.vr.fixture;

import br.com.vr.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class TransactionFixture {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static TransactionDTO defaultTransaction() {

        return TransactionDTO.builder()
            .cardNumber("1236547896541235")
            .password("1234")
            .value(BigDecimal.valueOf(10))
            .build();
    }

    public static TransactionDTO withPassword(final String password) {

        final var transaction = defaultTransaction();
        transaction.setPassword(password);
        return transaction;
    }

    public static TransactionDTO withValue(final BigDecimal value) {

        final var transaction = defaultTransaction();
        transaction.setValue(value);
        return transaction;
    }

    public static String jsonTransaction() {

        try {
            final var transactionDTO = defaultTransaction();
            return objectMapper.writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(transactionDTO);
        } catch (Exception ex) {
            return EMPTY;
        }
    }
}
