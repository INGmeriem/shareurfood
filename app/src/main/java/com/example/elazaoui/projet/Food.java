package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */

public class Food {

    private int idF;
    private String nameF;
    private String descriptionF;
    private double priceF;
    private String imageF;
    private int qtyF;
    private int typeF;
    private int idUF;

    public Food(String nameF, String descriptionF, double priceF, String imageF, int qtyF, int typeF) {
        this.nameF = nameF;
        this.descriptionF = descriptionF;
        this.priceF = priceF;
        this.imageF = imageF;
        this.qtyF = qtyF;
        this.typeF = typeF;
    }

    public int getIdF() {
        return idF;
    }

    public String getNameF() {
        return nameF;
    }

    public String getDescriptionF() {
        return descriptionF;
    }

    public double getPriceF() {
        return priceF;
    }

    public String getImageF() {
        return imageF;
    }

    public int getQtyF() {
        return qtyF;
    }

    public int getTypeF() {
        return typeF;
    }

    public int getIdUF() {
        return idUF;
    }
}

