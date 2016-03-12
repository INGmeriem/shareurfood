package com.example.elazaoui.projet;

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
}
