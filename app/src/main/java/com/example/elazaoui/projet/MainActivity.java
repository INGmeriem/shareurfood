package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    UserLocalStore userLocalStore;

    TextView txtName, txtAge, txtUsername, txtAddress, txtArrondissement;
    Button bLogout;
    Button bShare;
    Button bSearch;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtName = (TextView) findViewById(R.id.txtName);
        txtAge = (TextView) findViewById(R.id.txtAge);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        //txtArrondissement = (TextView) findViewById(R.id.txtArrondissement);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);

        bShare = (Button) findViewById(R.id.bShare);
        bShare.setOnClickListener(this);

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bShare:
                //userLocalStore.clearUserData();
                //userLocalStore.setUserLoggedIn(false);
                Intent shareIntent = new Intent(MainActivity.this, Share.class);
                startActivity(shareIntent);
                break;

            case R.id.bSearch:
                Intent searchIntent = new Intent(MainActivity.this, Search.class);
                startActivity(searchIntent);
                break;

            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent logoutIntent = new Intent(MainActivity.this, Login.class);
                startActivity(logoutIntent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        }
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        txtUsername.setText(user.username);
        txtName.setText(user.name);
        txtAge.setText(user.age + "");
        txtAddress.setText(user.adresse);


    }

}
