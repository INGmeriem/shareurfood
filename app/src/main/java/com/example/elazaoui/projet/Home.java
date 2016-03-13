package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */

import android.content.Intent;;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;



public class Home extends BaseActivity implements OnClickListener {

    private TextView name, age, user, address, postalcode, phone, email;
    private Button bLogout, bShare, bSearch;

    private static String MY_EMAIL = "nhbduy.iot@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send an email
                String[] addresses = {MY_EMAIL};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "{ShareUrFood] Contact request");
                intent.putExtra(Intent.EXTRA_TEXT, "Please send some information!");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        user = (TextView) findViewById(R.id.txtUsername);
        name = (TextView) findViewById(R.id.txtName);
        age = (TextView) findViewById(R.id.txtAge);
        address = (TextView) findViewById(R.id.txtAddress);
        postalcode = (TextView) findViewById(R.id.txtPostalCode);
        email = (TextView) findViewById(R.id.txtEmail);
        phone = (TextView) findViewById(R.id.txtPhone);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);

        bShare = (Button) findViewById(R.id.bShare);
        bShare.setOnClickListener(this);

        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(this);

        //load default SharedPreferences to TextView
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Home.this);
        user.setText(sp.getString("username", null));
        name.setText(sp.getString("name", null));
        age.setText(sp.getString("age", null));
        address.setText(sp.getString("address", null));
        postalcode.setText(sp.getString("postalcode", null));
        email.setText(sp.getString("email", null));
        phone.setText(sp.getString("phone", null));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bShare:
                Intent shareIntent = new Intent(Home.this, Share.class);
                startActivity(shareIntent);
                break;

            case R.id.bSearch:
                Intent searchIntent = new Intent(Home.this, Search.class);
                startActivity(searchIntent);
                break;

            case R.id.bLogout:
                //Clear all data cached in SharedPreferences when logout
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Home.this);
                sp.edit().clear().commit();

                Intent logoutIntent = new Intent(Home.this, Login.class);
                startActivity(logoutIntent);
                break;

            default:
                break;
        }
    }

}

