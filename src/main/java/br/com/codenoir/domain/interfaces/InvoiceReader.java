package br.com.codenoir.domain.interfaces;

import br.com.codenoir.model.Invoice;

import java.util.List;

public interface InvoiceReader {
    List<Invoice> read(String path) throws Exception;
}
