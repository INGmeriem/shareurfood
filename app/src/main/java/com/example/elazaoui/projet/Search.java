package com.example.elazaoui.projet;

/**
 * Created by DUYNGUYEN on 3/9/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Search extends BaseActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    private static final String SEARCH_URL = "http://shareurfood.nguyenhoangbaoduy.info/showfoodbyall.php";

    //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //An array of all of our foods
    private JSONArray mFoods = null;
    //manages all of our foods in a list.
    private ArrayList<HashMap<String, String>> mFoodList;

    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note that use read_comments.xml instead of our activity_row_food.xml
        setContentView(R.layout.activity_search);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlistView = (ListView) findViewById(R.id.listView);

        //loading the foods via AsyncTask
        new LoadFoods().execute();



    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //loading the foods via AsyncTask
        //new LoadFoods().execute();
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////

    /**
     * Retrieves json data of comments
     */
    public void updateJSONdata() {

        mFoodList = new ArrayList<HashMap<String, String>>();

        JSONParser jParser = new JSONParser();

        JSONObject json = jParser.getJSONFromUrl(SEARCH_URL);

        try {

            mFoods = json.getJSONArray("foods");

            // looping through all posts according to the json object returned
            for (int i = 0; i < mFoods.length(); i++) {
                JSONObject c = mFoods.getJSONObject(i);

                //gets the content of each tag
                String name = c.getString("name");
                String description = c.getString("description");
                String location = c.getString("postalcode");
                String price = c.getString("price");
                String image = c.getString("image");


                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("name", name);
                map.put("description", description);
                map.put("location", location);
                map.put("price", price);
                map.put("image", image);

                // adding HashList to ArrayList
                mFoodList.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Inserts the parsed data into our listview

    private void updateList() {

        //List<HashMap<String, String>> mData = GetSampleData();

        SimpleAdapter adapter = new SimpleAdapter(Search.this, mFoodList,
                R.layout.activity_row_food, new String[]{"name", "description", "location",
                "price", "image"}, new int[]{R.id.nameText, R.id.descriptionText,
                R.id.locationText, R.id.priceText, R.id.imageView});

        //Set the adapter to your ListView
        mlistView.setAdapter(adapter);


        mlistView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentFoodDetail = new Intent(Search.this, SearchDetail.class);
                startActivity(intentFoodDetail);

                Toast.makeText(Search.this, "You selected food items " + id + " and " + position, Toast.LENGTH_LONG).show();

            }
        });
    }

    public class LoadFoods extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Search.this);
            pDialog.setMessage("Loading Foods...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... arg0) {
            //we will develop this method in version 2
            updateJSONdata();

            return mFoodList;
        }


        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //we will develop this method in version 2
            updateList();
        }
    }
}
