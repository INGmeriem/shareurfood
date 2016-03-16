package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


        import android.content.Context;
        import android.content.SharedPreferences;

/**
 * Created by tundealao on 29/03/15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", user.getNameU());
        userLocalDatabaseEditor.putString("username", user.getUsernameU());
        userLocalDatabaseEditor.putString("password", user.getPasswordU());
        userLocalDatabaseEditor.putString("adresse", user.getAddressU());
        userLocalDatabaseEditor.putString("arrondissement", user.getPostalcodeU());
        userLocalDatabaseEditor.putInt("age", user.getAgeU());

        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String adresse = userLocalDatabase.getString("adresse", "");
        String arrondissement = userLocalDatabase.getString("arrondissement", "");

        int age = userLocalDatabase.getInt("age", -1);

        User user = new User(name, username, password, arrondissement);
        return user;
    }
}

