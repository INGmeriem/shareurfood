package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Share extends BaseActivity implements OnClickListener {

    private EditText name, description, price, image, qty, type;
    private Button mShare, mReset, mBack;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String SHARE_URL = "http://shareurfood.nguyenhoangbaoduy.info/addfood.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        name = (EditText)findViewById(R.id.etName);
        description = (EditText)findViewById(R.id.etDescription);
        price = (EditText)findViewById(R.id.etPrice);
        image = (EditText)findViewById(R.id.etImage);
        qty = (EditText)findViewById(R.id.etQuantity);
        type = (EditText)findViewById(R.id.etType);

        mShare = (Button) findViewById(R.id.bShare);
        mShare.setOnClickListener(this);
        mReset = (Button) findViewById(R.id.bClear);
        mReset.setOnClickListener(this);
        mBack = (Button) findViewById(R.id.bBack);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.bShare:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                String nameF = name.getText().toString();
                                String descriptionF = description.getText().toString();
                                String priceF = price.getText().toString();
                                String imageF = image.getText().toString();
                                String qtyF = qty.getText().toString();
                                String typeF = type.getText().toString();

                                new CreateFood().execute(nameF, descriptionF, priceF, imageF, qtyF, typeF);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Share.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                break;

            case R.id.bClear:
                name.getText().clear();
                description.getText().clear();
                price.getText().clear();
                image.getText().clear();
                image.getText().clear();
                qty.getText().clear();
                type.getText().clear();
                break;

            case R.id.bBack:
                finish();
                break;

            default:
                break;
        }

    }

    class CreateFood extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Share.this);
            pDialog.setMessage("Creating Food...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String name = args[0];
            String description = args[1];
            String price = args[2];
            String image = args[3];
            String qty = args[4];
            String type = args[5];

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Share.this);
            String user = sp.getString("username", null);

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("description", description));
                params.add(new BasicNameValuePair("price", price));
                params.add(new BasicNameValuePair("image", image));
                params.add(new BasicNameValuePair("qty", qty));
                params.add(new BasicNameValuePair("type", type));
                params.add(new BasicNameValuePair("user", user));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        SHARE_URL, "POST", params);

                // full json response
                Log.d("Add new food attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("New food Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("New food Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(Share.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}
