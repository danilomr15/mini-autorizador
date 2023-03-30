package br.com.vr.exception;

import br.com.vr.enums.TransactionStatusEnum;
import lombok.Getter;

@Getter
public class TransactionException extends RuntimeException {

    private final TransactionStatusEnum status;

    public TransactionException(final TransactionStatusEnum status) {

        this.status = status;
    }
}
