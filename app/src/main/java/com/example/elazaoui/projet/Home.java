package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */


import android.app.ProgressDialog;
import android.content.Intent;;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home extends BaseActivity implements OnClickListener {

    private TextView name, age, user, address, postalcode, phone, email;
    private Button bLogout, bShare, bSearch;

    private static String MY_EMAIL = "nhbduy.iot@gmail.com";

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String HOME_URL = "http://shareurfood.nguyenhoangbaoduy.info/showinfouser.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private ArrayList<String> infoUser;

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
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
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
                Intent logoutIntent = new Intent(Home.this, Login.class);
                startActivity(logoutIntent);
                break;

            default:
                break;
        }
    }

}

