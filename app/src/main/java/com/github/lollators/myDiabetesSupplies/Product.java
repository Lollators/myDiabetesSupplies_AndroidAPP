package com.github.lollators.myDiabetesSupplies;

public class Product {
    private String serialNumber;
    private String name;
    private String expirationDate;
    private String bin;

    public Product(String serialNumber, String name, String expirationDate, String bin) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.expirationDate = expirationDate;
        this.bin = bin;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
}
