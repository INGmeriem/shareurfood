package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



public class User {

    String name, username, password;
    String adresse;
    String arrondissement;
    int age;


    public User(String name, int age, String username, String password, String adresse, String arrondissement) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.adresse = adresse;
        this.arrondissement = arrondissement;


    }

    public User(String username, String password) {
        this("", -1, username, password, "","");
    }
}

