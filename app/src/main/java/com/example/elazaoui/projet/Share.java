




package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class Share extends AppCompatActivity implements View.OnClickListener {
    EditText etPlat;
    EditText etDescription;
    EditText etQuantite;
    EditText etPrix;
    EditText etType;
    Button bSend;

    UserLocalStore userLocalStore;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        etPlat = (EditText) findViewById(R.id.etPlat);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etQuantite = (EditText) findViewById(R.id.etQuantite);
        etPrix = (EditText) findViewById(R.id.etPrix);
        etType = (EditText) findViewById(R.id.etType);


        bSend = (Button) findViewById(R.id.bSend);

        bSend.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSend:
                String nomP = etPlat.getText().toString();
                String descriptionP = etDescription.getText().toString();
                int quantiteP = Integer.parseInt(etQuantite.getText().toString());

                float prixP = Float.parseFloat(etPrix.getText().toString());



                int typeP = Integer.parseInt(etType.getText().toString());
                String imgP= "test";
                Food food = new Food(nomP, descriptionP, prixP,imgP, quantiteP, typeP);
                registerFood(food);
                break;
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent Intent = new Intent(this, Login.class);
                startActivity(Intent);
                break;
        }
    }

    private void registerFood(Food food) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeFoodDataInBackground(food, new GetFoodCallback() {
            @Override
            public void done(Food returnedFood) {
                Intent Intent = new Intent(Share.this, Share.class);
                startActivity(Intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Share Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.elazaoui.projet/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Share Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.elazaoui.projet/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
