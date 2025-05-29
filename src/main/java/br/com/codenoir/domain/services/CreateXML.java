package br.com.codenoir.domain.services;

public class CreateXML {

    private final String invoice;
    private final String branchDestination;
    private final String asnDetail;

    public CreateXML(String invoice, String branchDestination, String asnDetail) {
        this.invoice = invoice;
        this.branchDestination = branchDestination;
        this.asnDetail = asnDetail;
    }

    public String getCompleteXml() {
        return "<tXML>" +
                "<Header>" +
                "<Source>Host</Source>" +
                "<Action_Type>update</Action_Type>" +
                "<Reference_ID>3651233</Reference_ID>" +
                "<Message_Type>ASN</Message_Type>" +
                "<Company_ID>1</Company_ID>" +
                "<Version>2016</Version>" +
                "</Header>" +
                "<Message>" +
                "<ASN>" +
                "<ASNID>" + this.getInvoice() + "</ASNID>" +
                "<ASNType>20</ASNType>" +
                "<ASNStatus>20</ASNStatus>" +
                "<BusinessPartnerCode>103472</BusinessPartnerCode>" +
                "<OriginFacilityAliasID>V103472</OriginFacilityAliasID>" +
                "<DestinationFacilityAliasID>" +
                this.getBranchDestination() +
                "</DestinationFacilityAliasID>" +
                "<DeliveryStart>11/11/2019 00:00:00</DeliveryStart>" +
                "<AssignedCarrierCode>100141</AssignedCarrierCode>" +
                "<ReferenceField1>Approved</ReferenceField1>" +
                "<ReferenceField2>104241</ReferenceField2>" +
                "<ReferenceField3>21401987000207</ReferenceField3>" +
                "<ReferenceField4>" + this.getInvoice() + "</ReferenceField4>" +
                "<ReferenceField5>2</ReferenceField5>" +
                "<ReferenceField6/>" +
                "<ReferenceField8>TRANSF</ReferenceField8>" +
                "<ExternalSysCreationDTTM>11/11/2019 00:00:00</ExternalSysCreationDTTM>" +
                "<OriginType>P</OriginType>" +
                "<DestinationType>W</DestinationType>" +
                "<IsCancelled>0</IsCancelled>" +
                "<TrailerNbr/>" +
                this.getAsnDetail() +
                "</ASN>" +
                "</Message>" +
                "</tXML>";
    }

    public String getInvoice() {
        return invoice;
    }

    public String getBranchDestination() {
        return branchDestination;
    }


    public String getAsnDetail() {
        return asnDetail;
    }

}
