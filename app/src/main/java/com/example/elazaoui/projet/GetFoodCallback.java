package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


interface GetFoodCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(Food returnedFood);
}
