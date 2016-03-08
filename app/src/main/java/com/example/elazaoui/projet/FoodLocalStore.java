package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tundealao on 29/03/15.
 */
public class FoodLocalStore {

    public static final String SP_NAME = "foodDetails";

    SharedPreferences foodLocalDatabase;

    public FoodLocalStore(Context context) {
        foodLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeFoodData(Food food) {
        SharedPreferences.Editor foodLocalDatabaseEditor = foodLocalDatabase.edit();
        foodLocalDatabaseEditor.putString("nomP", food.nomP);
        foodLocalDatabaseEditor.putString("descriptionP", food.descriptionP);
        foodLocalDatabaseEditor.putFloat("prixP", (float) food.prixP);
        foodLocalDatabaseEditor.putInt("quantiteP", food.quantiteP);
        foodLocalDatabaseEditor.putInt("typeP", food.typeP);

        foodLocalDatabaseEditor.commit();
    }

    public void setFoodLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor foodLocalDatabaseEditor = foodLocalDatabase.edit();
        foodLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        foodLocalDatabaseEditor.commit();
    }

    public void clearFoodData() {
        SharedPreferences.Editor foodLocalDatabaseEditor = foodLocalDatabase.edit();
        foodLocalDatabaseEditor.clear();
        foodLocalDatabaseEditor.commit();
    }

    public Food getLoggedInFood() {
        if (foodLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String nomP = foodLocalDatabase.getString("nomP", "");
        String descriptionP = foodLocalDatabase.getString("descriptionP", "");
        int quantiteP = foodLocalDatabase.getInt("quantiteP", -1);
        double prixP = foodLocalDatabase.getFloat("prixP", -1);
        int typeP = foodLocalDatabase.getInt("typeP", -1);
        String imgP = foodLocalDatabase.getString("imgP", "");

        Food food = new Food(nomP, descriptionP, prixP, imgP, quantiteP, typeP);

        return food;
    }
}

