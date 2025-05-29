package br.com.codenoir.exceptions;

public class FileReadException extends RuntimeException {
    public FileReadException(String message) {
        super(message);
    }
}
