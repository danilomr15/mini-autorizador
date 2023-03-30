package br.com.vr.exception;

public class MessagingException extends RuntimeException {

    public MessagingException() {

        super("Erro ao tentar enviar mensagem");
    }
}
