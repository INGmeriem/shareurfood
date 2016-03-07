package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(User returnedUser);
}
