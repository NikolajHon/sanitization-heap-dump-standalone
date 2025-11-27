package com.parkdots.permit.applicationb.model;

public class ProductOrder {

    private String orderId;
    private String productName;
    private String ownerName;
    private String ownerEmail;
    private String ownerSurname;
    private byte[] details;


    public ProductOrder(String orderId, String productName, String ownerName, String ownerEmail, String ownerSurname, byte[] details) {
        this.orderId = orderId;
        this.productName = productName;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerSurname = ownerSurname;
        this.details = details;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public byte[] getDetails() {
        return details;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }
}
