package br.com.codenoir.exceptions;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String message) {
        super(message);
    }
}
