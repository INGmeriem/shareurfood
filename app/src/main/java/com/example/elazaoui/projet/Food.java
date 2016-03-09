package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



public class Food {

    String nomP;
    String descriptionP;
    double prixP;
    String imgP;
    int quantiteP;
    int typeP;

    public Food(String nomP, String descriptionP, double prixP, String imgP, int quantiteP, int typeP) {
        this.nomP = nomP;
        this.descriptionP = descriptionP;
        this.prixP = prixP;
        this.imgP = imgP;
        this.quantiteP = quantiteP;
        this.typeP = typeP;
    }

    @Override
    public String toString() {
        return "Food{" +
                "nomP='" + nomP + '\'' +
                ", descriptionP='" + descriptionP + '\'' +
                ", prixP=" + prixP +
                ", imgP='" + imgP + '\'' +
                ", quantiteP=" + quantiteP +
                ", typeP=" + typeP +
                '}';
    }
}

