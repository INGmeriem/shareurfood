package com.example.elazaoui.projet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DUYNGUYEN on 3/14/2016.
 */
public class SearchDetail extends BaseActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String ADDTOCART_URL = "http://shareurfood.nguyenhoangbaoduy.info/addfoodtocart.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    HashMap<String, String> foodItem = new HashMap<String, String>();

    String foodPosition = null;

    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutFoodDetail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                /*Intent data = new Intent();
                                data.putExtra(Search.RETURN_MESSAGE,
                                        "\"" + foodItem.get("name") + "\"" + " added to cart");
                                setResult(RESULT_OK, data);
                                finish();*/

                                // save user data
                                SharedPreferences sp = PreferenceManager
                                        .getDefaultSharedPreferences(SearchDetail.this);
                                SharedPreferences.Editor edit = sp.edit();

                                edit.putString("idF", foodItem.get("id"));

                                edit.commit();

                                String message = "\"" + foodItem.get("name") + "\" added";
                                Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG)
                                        .setAction("Go to cart", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Intent cartIntent = new Intent(SearchDetail.this, Cart.class);
                                                //startActivity(cartIntent);

                                                Toast.makeText(SearchDetail.this,
                                                        "Going to cart", Toast.LENGTH_SHORT).show();
                                            }
                                        }).show();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchDetail.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


        try {
            foodPosition = getIntent().getStringExtra(Search.FOOD_POSITION);

            foodItem = Search.mFoodList.get(Integer.valueOf(foodPosition));

            TextView name = (TextView) findViewById(R.id.nameText);
            name.setText(foodItem.get("name"));

            TextView type = (TextView) findViewById(R.id.typeText);
            type.setText(foodItem.get("type"));

            TextView price = (TextView) findViewById(R.id.priceText);
            price.setText(foodItem.get("price"));

            TextView postalcode = (TextView) findViewById(R.id.locationText);
            postalcode.setText(foodItem.get("location"));

            TextView user = (TextView) findViewById(R.id.userText);
            user.setText(foodItem.get("user"));

            TextView description = (TextView) findViewById(R.id.descriptionText);
            description.setText(foodItem.get("description"));

            try {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                Picasso.with(SearchDetail.this).load(foodItem.get("image")).into(image);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();

            Intent searchIntent = new Intent(SearchDetail.this, Search.class);
            startActivity(searchIntent);
        }

    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        SearchDetail.this.finish();
    }

    //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    class AddToCart extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchDetail.this);
            pDialog.setMessage("Adding Food to Cart...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            // Check for success tag
            int success;

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SearchDetail.this);
            String idFC = sp.getString("idF", null);
            String userUC = sp.getString("username", null);
            int qtyFC = 1;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("food", idFC));
                params.add(new BasicNameValuePair("qty", String.valueOf(qtyFC)));
                params.add(new BasicNameValuePair("username", userUC));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        ADDTOCART_URL, "POST", params);

                // full json response
                Log.d("Add to Cart attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Added Food to Cart!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Add to Cart Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(SearchDetail.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
