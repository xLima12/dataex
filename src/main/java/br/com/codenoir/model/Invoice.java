package br.com.codenoir.model;

public class Invoice {

    private String branchOrigin;
    private String branchDestination;
    private int code;
    private String description;
    private int units;
    private String invoice;

    public String getBranchOrigin() {
        return branchOrigin;
    }

    public void setBranchOrigin(String branchOrigin) {
        this.branchOrigin = branchOrigin;
    }

    public String getBranchDestination() {
        return branchDestination;
    }

    public void setBranchDestination(String branchDestination) {
        this.branchDestination = branchDestination;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
