package br.com.codenoir.exceptions;

public class InvalidBranchException extends RuntimeException {
    public InvalidBranchException(String message) {
        super(message);
    }
}
