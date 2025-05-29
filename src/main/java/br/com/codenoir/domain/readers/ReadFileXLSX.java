package br.com.codenoir.domain.readers;

import br.com.codenoir.domain.interfaces.InvoiceReader;
import br.com.codenoir.exceptions.FileEmptyException;
import br.com.codenoir.exceptions.MissingOrInvalidHeaderException;
import br.com.codenoir.model.Invoice;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class ReadFileXLSX implements InvoiceReader {

    private static final Map<String, String> HEADER_ALIASES = new HashMap<>();

    static {
        HEADER_ALIASES.put("", "branchOrigin");
        HEADER_ALIASES.put("FILIALORIGEM", "branchOrigin");
        HEADER_ALIASES.put("FILIAL ORIGEM", "branchOrigin");

        HEADER_ALIASES.put("FILIALDESTINO", "branchDestination");
        HEADER_ALIASES.put("FILIAL DESTINO", "branchDestination");

        HEADER_ALIASES.put("NF", "invoice");

        HEADER_ALIASES.put("COD", "code");
        HEADER_ALIASES.put("CODIGO", "code");

        HEADER_ALIASES.put("PRODUTO", "description");
        HEADER_ALIASES.put("DESCRIÇÃO", "description");
        HEADER_ALIASES.put("DESCRICAO", "description");

        HEADER_ALIASES.put("QTDE", "units");
        HEADER_ALIASES.put("QTD", "units");
        HEADER_ALIASES.put("UNIDADES", "units");
    }

    @Override
    public List<Invoice> read(String pathFile) throws Exception {
        List<Invoice> invoices = new ArrayList<>();

        try(InputStream is = new FileInputStream(pathFile);
            Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if(!rowIterator.hasNext()) {
                throw new FileEmptyException("Excel está vazio.");
            }

            Row headerRow = rowIterator.next();
            Map<String, Integer> columnsMapped = new HashMap<>();

            for(Cell cell : headerRow) {
                String name = cell.getStringCellValue().trim().toUpperCase();
                String standard = HEADER_ALIASES.get(name);
                if(standard != null) {
                    columnsMapped.put(standard, cell.getColumnIndex());
                }
            }

            List<String> requiredFields = Arrays.asList(
                    "branchOrigin", "branchDestination", "invoice", "code", "description", "units");

            for(String field : requiredFields) {
                if(!columnsMapped.containsKey(field)) {
                    throw new MissingOrInvalidHeaderException("Cabeçalhos ausentes ou incorretos para o campo: " + field);
                }
            }

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if(isRowEmpty(row)) {
                    continue;
                }

                Invoice invoice = new Invoice();

                String possibleCode = getStringCell(row, columnsMapped.get("code"));
                String possibleDescription = getStringCell(row, columnsMapped.get("description"));

                if(isOnlyNumbers(possibleCode)) {
                    invoice.setCode(Integer.parseInt(possibleCode));
                    invoice.setDescription(possibleDescription);
                }

                if(isOnlyNumbers(possibleDescription)) {
                    invoice.setCode(Integer.parseInt(possibleDescription));
                    invoice.setDescription(possibleCode);
                }

                invoice.setBranchOrigin(getStringCell(row, columnsMapped.get("branchOrigin")));
                invoice.setBranchDestination(getStringCell(row, columnsMapped.get("branchDestination")));
                invoice.setInvoice(getStringCell(row, columnsMapped.get("invoice")));
                invoice.setUnits((int) getNumericCell(row, columnsMapped.get("units")));

                invoices.add(invoice);
            }
        }
        return invoices;
    }

    private String getStringCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private double getNumericCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if(cell == null) return 0;
        if(cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return cell.getNumericCellValue();
    }

    private boolean isOnlyNumbers(String value) {
        if (value == null) return false;
        String clean = value.replaceAll("[.,\\s]", "");
        return clean.matches("\\d+");
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK && !getStringCell(row, i).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
