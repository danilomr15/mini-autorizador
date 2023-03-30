package br.com.vr.exception;

import br.com.vr.dto.CardDTO;
import br.com.vr.dto.ErrorMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<CardDTO> handle(final CardAlreadyExistsException ex) {

        return ResponseEntity.unprocessableEntity().body(ex.getCardDTO());
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Void> handle(final CardNotFoundException ex) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<String> handle(final TransactionException ex) {

        return ResponseEntity.unprocessableEntity().body(ex.getStatus().getDescription());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> handle(final MessagingException ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> handle(final MethodArgumentNotValidException ex) {

        final var message = ex.getBindingResult().getAllErrors().stream()
            .filter(Objects::nonNull)
            .findFirst()
            .map(ObjectError::getDefaultMessage)
            .orElse(null);

        final var errorMessage = ErrorMessageDTO.builder()
            .message(message)
            .build();

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handle(final Exception ex) {

        final var errorMessage = ErrorMessageDTO.builder()
            .message("Erro interno")
            .build();

        return ResponseEntity.internalServerError().body(errorMessage);
    }
}
