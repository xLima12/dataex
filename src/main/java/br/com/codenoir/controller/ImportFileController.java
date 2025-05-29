package br.com.codenoir.controller;

import br.com.codenoir.domain.services.CreateAsnDetail;
import br.com.codenoir.domain.services.CreateXML;
import br.com.codenoir.domain.factory.InvoiceReaderFactory;
import br.com.codenoir.domain.interfaces.InvoiceReader;
import br.com.codenoir.exceptions.*;
import br.com.codenoir.model.Invoice;

import java.util.List;

public class ImportFileController {

    private String pathFile;
    private String xmlComplete;
    private String mountAsnDetail = "";
    private String invoice = "";
    private String branchDestination = "";
    private String fullBranchDestination = "";
    private int count = 0;
    private int units = 0;

    public void generatedXml(String pathFile) {
        this.setPathFile(pathFile);

        try {
            InvoiceReader reader = InvoiceReaderFactory.create(this.getPathFile());
            List<Invoice> invoices = reader.read(this.getPathFile());

            this.setInvoice(invoices.get(0).getInvoice());
            this.setBranchDestination(invoices.get(0).getBranchDestination().substring(0,
                    invoices.get(0).getBranchDestination().indexOf("-")));
            this.setFullBranchDestination(invoices.get(0).getBranchDestination());

            for (Invoice value : invoices) {
                this.setCount(this.getCount()+1);
                if(!value.getBranchDestination().substring(0, value.getBranchDestination().indexOf("-")).equals(this.getBranchDestination())) {
                    throw new InvalidBranchException("Falha na validação: as linhas do arquivo contêm filiais de destino diferentes.\n" +
                            "Verifique e corrija antes de gerar o XML.");
                }
                CreateAsnDetail createAsnDetail = new CreateAsnDetail(this.getCount(), value.getCode(), this.getInvoice(), value.getUnits());
                this.setUnits(this.getUnits() + value.getUnits());
                this.setMountAsnDetail(this.getMountAsnDetail().concat(createAsnDetail.getAsnDetail()));
            }

            CreateXML createXML = new CreateXML(this.getInvoice(), this.getBranchDestination(), this.getMountAsnDetail());

            this.setXmlComplete(createXML.getCompleteXml());

        } catch (InvalidBranchException e) {
            throw e;
        } catch (FileEmptyException e) {
            throw new FileEmptyException(e.getMessage());
        } catch (MissingOrInvalidHeaderException e) {
            throw new MissingOrInvalidHeaderException(e.getMessage());
        } catch (UnsupportedFileEncodingException e) {
            throw new UnsupportedFileEncodingException(e.getMessage());
        } catch (Exception e) {
            throw new FileReadException(e.getMessage());
        }
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getXmlComplete() {
        return xmlComplete;
    }

    public void setXmlComplete(String xmlComplete) {
        this.xmlComplete = xmlComplete;
    }

    public String getMountAsnDetail() {
        return mountAsnDetail;
    }

    public void setMountAsnDetail(String mountAsnDetail) {
        this.mountAsnDetail = mountAsnDetail;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getBranchDestination() {
        return branchDestination;
    }

    public void setBranchDestination(String branchDestination) {
        this.branchDestination = branchDestination;
    }

    public String getFullBranchDestination() {
        return fullBranchDestination;
    }

    public void setFullBranchDestination(String fullBranchDestination) {
        this.fullBranchDestination = fullBranchDestination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

}
