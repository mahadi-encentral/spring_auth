package com.mamt4real.authentication.ephyto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillInfoResponse {
    private long id;
    private String code;
    private String inspectorConf;
    private String inspectorName;
    private String cashierConf;
    private String exporterName;
    private String exporterAddress;
    private String exporterEmail;
    private String exporterPhone;
    private List<BillingDetail> billingDetails;

    // Getters and setters for the fields

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInspectorConf() {
        return inspectorConf;
    }

    public void setInspectorConf(String inspectorConf) {
        this.inspectorConf = inspectorConf;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getCashierConf() {
        return cashierConf;
    }

    public void setCashierConf(String cashierConf) {
        this.cashierConf = cashierConf;
    }

    public String getExporterName() {
        return exporterName;
    }

    public void setExporterName(String exporterName) {
        this.exporterName = exporterName;
    }

    public String getExporterAddress() {
        return exporterAddress;
    }

    public void setExporterAddress(String exporterAddress) {
        this.exporterAddress = exporterAddress;
    }

    public String getExporterEmail() {
        return exporterEmail;
    }

    public void setExporterEmail(String exporterEmail) {
        this.exporterEmail = exporterEmail;
    }

    public String getExporterPhone() {
        return exporterPhone;
    }

    public void setExporterPhone(String exporterPhone) {
        this.exporterPhone = exporterPhone;
    }

    public List<BillingDetail> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(List<BillingDetail> billingDetails) {
        this.billingDetails = billingDetails;
    }

    // BillingDetail DTO class for the nested structure
    public static class BillingDetail {
        private double price;
        private String code;
        private String description;

        public BillingDetail() {
        }

        public BillingDetail(double price, String code, String description) {
            this.price = price;
            this.code = code;
            this.description = description;
        }


        // Getters and setters for BillingDetail fields

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

