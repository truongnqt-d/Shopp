package com.example.shoppingapp.dataFirebase;

public class Credit {
    private String cardNumber;
    private String expirationDate;
    private String verificationCodes;
    private String cardHolder;
    private String address;
    private String postalCode;
    private String idUser;

    public Credit() {
    }

    public Credit(String cardNumber, String expirationDate, String verificationCodes, String cardHolder, String address, String postalCode, String idUser) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.verificationCodes = verificationCodes;
        this.cardHolder = cardHolder;
        this.address = address;
        this.postalCode = postalCode;
        this.idUser = idUser;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getVerificationCodes() {
        return verificationCodes;
    }

    public void setVerificationCodes(String verificationCodes) {
        this.verificationCodes = verificationCodes;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
