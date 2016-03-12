package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    UserLocalStore userLocalStore;

    TextView txtName, txtAge, txtUsername, txtAddress, txtArrondissement;
    Button bLogout;
    Button bShare;
    Button bSearch;

    private static final int MENU_ITEM_LOGOUT = 1001;

    private RelativeLayout relativeLayout;

    private static String email = "nhbduy.iot@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send an email
                String[] addresses = {email};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
                intent.putExtra(Intent.EXTRA_TEXT, "Please send some information!");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0, MENU_ITEM_LOGOUT, 1001, R.string.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Snackbar.make(relativeLayout,
                        "You selected settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(this, "You selected settings", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                //Intent intent = new Intent(this, AboutActivity.class);
                //startActivity(intent);
                return true;
            case R.id.action_account:
                //Go to my account
                /*Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                }*/
                //Intent intent = new Intent(this, AccountActivity.class);
                //startActivity(intent);
                return true;
            case R.id.action_cart:
                Toast.makeText(this, "You selected the Shopping Cart", Toast.LENGTH_LONG).show();
                return true;
            case MENU_ITEM_LOGOUT:
                /*Snackbar.make(relativeLayout,
                        "You selected Logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Toast.makeText(this, "You selected Logout", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
