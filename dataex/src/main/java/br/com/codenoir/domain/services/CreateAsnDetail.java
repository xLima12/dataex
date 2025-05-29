package br.com.codenoir.domain.services;

public class CreateAsnDetail {

    private final int sequenceNumber;
    private final int itemName;
    private final String referenceField;
    private final int units;

    public CreateAsnDetail(int sequenceNumber, int itemName, String referenceField, int units) {
        this.sequenceNumber = sequenceNumber;
        this.itemName = itemName;
        this.referenceField = referenceField;
        this.units = units;
    }

    public String getAsnDetail() {
        return "<ASNDetail>" +
                "<SequenceNumber>" + this.getSequenceNumber() + "</SequenceNumber>" +
                "<BusinessPartnerCode>103472</BusinessPartnerCode>" +
                "<ItemName>" + this.getItemName() + "</ItemName>" +
                "<ProcessImmdNeeds>Y</ProcessImmdNeeds>" +
                "<IsCancelled>0</IsCancelled>" +
                "<ReferenceField1/>" +
                "<ReferenceField3>*</ReferenceField3>" +
                "<ReferenceField4>" + this.getReferenceField() + "</ReferenceField4>" +
                "<InventoryAttributes>" +
                "<BatchNumber>*</BatchNumber>" +
                "</InventoryAttributes>" +
                "<Quantity>" +
                "<ShippedQty>" + this.getUnits() + "</ShippedQty>" +
                "<QtyUOM>Unit</QtyUOM>" +
                "</Quantity>" +
                "</ASNDetail>";
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public int getItemName() {
        return itemName;
    }

    public String getReferenceField() {
        return referenceField;
    }

    public int getUnits() {
        return units;
    }

}
