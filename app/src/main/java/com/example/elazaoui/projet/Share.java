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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Share extends BaseActivity implements OnClickListener {

    private EditText name, description, price, image, qty, type;
    private Button mShare, mReset, mBack;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String SHARE_URL = "http://shareurfood.nguyenhoangbaoduy.info/addfood.php";
    private static final String TYPE_URL = "http://shareurfood.nguyenhoangbaoduy.info/showfoodtype.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private SimpleAdapter adapterFoodTypes;

    private Spinner cmbFoodTypes;

    //Food types
    private List<HashMap<String, String>> lstFoodTypes;

    private String selectedFoodTypeId = null;

    //private int counter;


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

        //type = (EditText)findViewById(R.id.etType);

        new LoadFoodTypeAsyncTask().execute();

        /*cmbFoodTypes = (Spinner) findViewById(R.id.spinnerType);
        adapterFoodTypes = new SimpleAdapter(this, lstFoodTypes, R.layout.activity_foodtype,
                new String[] {"id", "name"}, new int[] {0, R.id.tvFoodType});
        cmbFoodTypes.setAdapter(adapterFoodTypes);
        cmbFoodTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                HashMap<String, String> map = lstFoodTypes.get(position);

                String type = map.get("name");

                Toast.makeText(Share.this, type, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                HashMap<String, String> map = lstFoodTypes.get(arg2);
            String name = map.get(NAME);
            if (name.equalsIgnoreCase(ADD_NEW_ITEM)) {
                lstNames.remove(map);
                counter++;
                addNewName(String.valueOf(counter));
                addNewName(ADD_NEW_ITEM);
                adapter.notifyDataSetChanged();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                Toast.makeText(Share.this, "This is food type", Toast.LENGTH_LONG).show();
            }
        });*/

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

                                //String typeF = type.getText().toString();
                                //String typeF = cmbFoodTypes.getSelectedItem().toString();

                                String typeF = selectedFoodTypeId;

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
                //type.getText().clear();
                break;

            case R.id.bBack:
                finish();
                break;

            default:
                break;
        }

    }

    class LoadFoodTypeAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            lstFoodTypes = new ArrayList<HashMap<String, String>>();

            JSONArray mFoodTypes = null;

            try {

                Log.d("request!", "starting");

                JSONObject json = jsonParser.getJSONFromUrl(TYPE_URL);

                mFoodTypes = json.getJSONArray("foodtypes");

                for (int i = 0; i < mFoodTypes.length(); i++) {
                    JSONObject c = mFoodTypes.getJSONObject(i);

                    int id = c.getInt("id");
                    String name = c.getString("name");

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("id", String.valueOf(id));
                    map.put("name", name);

                    //Add to ArrayList
                    lstFoodTypes.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            cmbFoodTypes = (Spinner) findViewById(R.id.spinnerType);
            adapterFoodTypes = new SimpleAdapter(Share.this, lstFoodTypes, R.layout.activity_foodtype,
                    new String[] {"id", "name"}, new int[] {0, R.id.tvFoodType});
            cmbFoodTypes.setAdapter(adapterFoodTypes);
            cmbFoodTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    //Log.v("item", (String) parent.getItemAtPosition(position));

                    HashMap<String, String> map = lstFoodTypes.get(position);

                    String idTF = map.get("id");
                    selectedFoodTypeId = idTF;

                    String nameTF = map.get("name");


                    Toast.makeText(Share.this, idTF + " " + nameTF, Toast.LENGTH_LONG).show();
                }

            /*@Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                HashMap<String, String> map = lstFoodTypes.get(arg2);
            String name = map.get(NAME);
            if (name.equalsIgnoreCase(ADD_NEW_ITEM)) {
                lstNames.remove(map);
                counter++;
                addNewName(String.valueOf(counter));
                addNewName(ADD_NEW_ITEM);
                adapter.notifyDataSetChanged();
            }
            }*/

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    ///////////////////////////////////////////////////////////////////////
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
