package br.com.codenoir.exceptions;

public class MissingOrInvalidHeaderException extends RuntimeException {
    public MissingOrInvalidHeaderException(String message) {
        super(message);
    }
}
