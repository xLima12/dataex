package br.com.codenoir.domain.factory;

import br.com.codenoir.domain.readers.ReadFileCSV;
import br.com.codenoir.domain.readers.ReadFileXLS;
import br.com.codenoir.domain.readers.ReadFileXLSX;
import br.com.codenoir.domain.interfaces.InvoiceReader;

public class InvoiceReaderFactory {
    public static InvoiceReader create(String path) {
        if(path.endsWith(".csv")) return new ReadFileCSV();
        if(path.endsWith(".xls")) return new ReadFileXLS();
        if(path.endsWith(".xlsx")) return new ReadFileXLSX();
        throw new IllegalArgumentException("Unsupported file format: " + path);
    }
}
