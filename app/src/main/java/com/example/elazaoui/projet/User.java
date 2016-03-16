package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



public class User {
    private int idU;
    private String nameU;
    private int ageU;
    private String usernameU;
    private String passwordU;
    private String addressU;
    private String postalcodeU;
    private String emailU;
    private String phoneU;

    public User(String nameU, String usernameU, String passwordU, String postalcodeU) {
        this.nameU = nameU;
        this.usernameU = usernameU;
        this.passwordU = passwordU;
        this.postalcodeU = postalcodeU;
    }

    public User(String nameU, int ageU, String usernameU, String passwordU, String addressU, String postalcodeU, String emailU, String phoneU) {
        this.nameU = nameU;
        this.ageU = ageU;
        this.usernameU = usernameU;
        this.passwordU = passwordU;
        this.addressU = addressU;
        this.postalcodeU = postalcodeU;
        this.emailU = emailU;
        this.phoneU = phoneU;
    }

    public int getIdU() {
        return idU;
    }

    public String getNameU() {
        return nameU;
    }

    public int getAgeU() {
        return ageU;
    }

    public String getUsernameU() {
        return usernameU;
    }

    public String getPasswordU() {
        return passwordU;
    }

    public String getAddressU() {
        return addressU;
    }

    public String getPostalcodeU() {
        return postalcodeU;
    }

    public String getEmailU() {
        return emailU;
    }

    public String getPhoneU() {
        return phoneU;
    }
}

