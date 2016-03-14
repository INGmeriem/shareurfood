package com.example.elazaoui.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by DUYNGUYEN on 3/12/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;

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
                Toast.makeText(this, "You selected settings", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "You selected the About Section", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_account:
                Toast.makeText(this, "You selected the Account Management Section", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_cart:
                Toast.makeText(this, "You selected the Shopping Cart", Toast.LENGTH_LONG).show();
                return true;
            case MENU_ITEM_LOGOUT:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
                sp.edit().clear().commit();

                Intent logoutIntent = new Intent(BaseActivity.this, Login.class);
                startActivity(logoutIntent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
