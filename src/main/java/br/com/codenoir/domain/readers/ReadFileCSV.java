package br.com.codenoir.domain.readers;

import br.com.codenoir.domain.interfaces.InvoiceReader;
import br.com.codenoir.exceptions.FileEmptyException;
import br.com.codenoir.exceptions.UnsupportedFileEncodingException;
import br.com.codenoir.model.Invoice;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFileCSV implements InvoiceReader {

    private static final Map<String, String> HEADER_ALIASES = new HashMap<>();

    static {
        HEADER_ALIASES.put("FILIALORIGEM", "branchOrigin");
        HEADER_ALIASES.put("FILIAL ORIGEM", "branchOrigin");

        HEADER_ALIASES.put("FILIALDESTINO", "branchDestination");
        HEADER_ALIASES.put("FILIAL DESTINO", "branchDestination");

        HEADER_ALIASES.put("NF", "invoice");

        HEADER_ALIASES.put("PRODUTO", "code");

        HEADER_ALIASES.put("DESCRIÇÃO", "description");
        HEADER_ALIASES.put("DESCRICAO", "description");

        HEADER_ALIASES.put("QTDE", "units");
        HEADER_ALIASES.put("QTD", "units");
        HEADER_ALIASES.put("UNIDADES", "units");
    }

    @Override
    public List<Invoice> read(String pathFile) throws IOException {

        List<Invoice> invoices = new ArrayList<>();

        Map<String, Integer> indexMap = new HashMap<>();

        String[] encodings = {"UTF-8", "ISO-8859-1", "Windows-1252", "UTF-16"};

        for (String encoding : encodings) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(pathFile), encoding))) {

                String headerLine = reader.readLine();
                if (headerLine == null) throw new FileEmptyException("CSV está vazio");

                if (headerLine.contains("��") || headerLine.contains("?")) {
                    continue;
                }

                String separator = headerLine.contains("\t") ? "\t" : ";";
                String[] headers = headerLine.split(separator, -1);

                indexMap.clear();

                for (int i = 0; i < headers.length; i++) {
                    String normalized = headers[i].trim().toUpperCase();
                    String field = HEADER_ALIASES.get(normalized);
                    if (field != null) {
                        indexMap.put(field, i);
                    }
                }

                System.out.println("Index map: " + indexMap);

                if (!indexMap.isEmpty()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(separator, -1);
                        if (values.length == 0 || Arrays.stream(values).allMatch(String::isEmpty)) continue;

                        Invoice invoice = new Invoice();

                        invoice.setBranchOrigin(getValue(values, indexMap, "branchOrigin"));
                        invoice.setBranchDestination(getValue(values, indexMap, "branchDestination"));
                        invoice.setInvoice(getValue(values, indexMap, "invoice"));

                        String rawCode = getValue(values, indexMap, "code");
                        invoice.setCode(extractLeadingNumber(rawCode));

                        invoice.setDescription(getValue(values, indexMap, "description"));

                        String rawUnits = getValue(values, indexMap, "units");
                        invoice.setUnits(isOnlyNumbers(rawUnits) ? Integer.parseInt(rawUnits) : 0);

                        invoices.add(invoice);
                    }

                    return invoices;
                }
            } catch (IOException e) {
                continue;
            }
        }

        throw new UnsupportedFileEncodingException("Não foi possível ler o arquivo com nenhuma codificação suportada.");
    }

    private String getValue(String[] values, Map<String, Integer> indexMap, String key) {
        Integer idx = indexMap.get(key);
        if (idx != null && idx < values.length) {
            return values[idx].trim();
        }
        return null;
    }

    private Integer extractLeadingNumber(String input) {
        if (input == null) return 0;
        Matcher matcher = Pattern.compile("^(\\d+)").matcher(input.trim());
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private boolean isOnlyNumbers(String value) {
        if (value == null) return false;
        String clean = value.replaceAll("[.,\\s]", "");
        return clean.matches("\\d+");
    }
}