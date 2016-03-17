package com.example.elazaoui.projet;

/**
 * Created by DUYNGUYEN on 3/9/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Search extends BaseActivity {


    JSONParser jParser = new JSONParser();

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
    private ArrayList<HashMap<String, String>> mFilteredFoodList;

    private ListView mlistView;

    //Search
    View myView;
    SearchView mySearchview;
    ImageButton buttonAudio;
    ImageButton buttonMaps;
    String found = "N";
    String textSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note that use read_comments.xml instead of our activity_row_food.xml
        setContentView(R.layout.activity_search);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlistView = (ListView) findViewById(R.id.listView);

        //loading the foods via AsyncTask (first time)
        String key = null;
        new LoadFoods().execute(key);

        //Not use AsynTask for the first time
        //updateJSONdata();
        //updateList();

        //loading action for search bar
        loadSearchBar();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //loading the foods via AsyncTask
        new LoadFoods().execute(textSearch);
    }

    public void loadSearchBar() {
        mySearchview = (SearchView) findViewById(R.id.searchView1);

        mySearchview.setQueryHint("Search by name, food type or postal code...");

        buttonAudio = (ImageButton) findViewById(R.id.bAudio);

        buttonMaps = (ImageButton) findViewById(R.id.bMaps);

        mySearchview.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(Search.this, "Let's eat... :-)", Toast.LENGTH_SHORT).show();
            }
        });

        mySearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                new LoadFoods().execute(newText);

                return false;
            }
        });

        //Recognize voice speed search
        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hurry uppp! Please start speaking loudly : food name, type or postal code whatever you want!");
                voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRENCH);
                startActivityForResult(voiceIntent, 1);
            }
        });

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Voice search
        if(requestCode == 1) {
            ArrayList<String> results;
            results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String text = results.get(0).replace("'", "");
            mySearchview.setQuery(text, true);
            mySearchview.setIconifiedByDefault(false);
        }
        //Maps search
        if(requestCode == 2) {

        }
    }



    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    public class LoadFoods extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Search.this);
            pDialog.setMessage("Loading Foods...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            //pDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            //we will develop this method in version 2

            textSearch = args[0];

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

    /**
     * Retrieves json data of comments
     */
    public void updateJSONdata() {

        mFoodList = new ArrayList<HashMap<String, String>>();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keyword", textSearch));

            Log.d("request!", "starting");

            JSONObject json = jParser.getJSONFromUrl(SEARCH_URL);

            //Posting user data to script
            json = jParser.makeHttpRequest(SEARCH_URL, "POST", params);

            mFoods = json.getJSONArray("foods");

            String matchFound = "N";

            // looping through all posts according to the json object returned
            for (int i = 0; i < mFoods.length(); i++) {
                JSONObject c = mFoods.getJSONObject(i);

                //gets the content of each tag
                int id = c.getInt("id");
                String name = c.getString("name");
                String description = c.getString("description");
                String location = c.getString("postalcode");
                String price = c.getString("price");
                String image = c.getString("image");


                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("id", String.valueOf(id));
                map.put("name", name);
                map.put("description", description);
                map.put("location", location);
                map.put("price", price);
                map.put("image", image);

                //Check if this food is already there in mFoodList. If yes, we don't add it again.
                for (int j = 0; i > mFoodList.size(); j++) {
                    if (mFoodList.get(j).get("id").equals(map.get("id"))) {
                        matchFound = "Y";
                    }
                }

                if(matchFound == "N") {
                    // adding HashList to ArrayList
                    mFoodList.add(map);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Inserts the parsed data into our listview
    private void updateList() {

        SimpleAdapter adapter = new SimpleAdapter(Search.this, mFoodList,
                R.layout.activity_row_food, new String[]{"id", "name", "description", "location",
                "price", "image"}, new int[]{0, R.id.nameText, R.id.descriptionText,
                R.id.locationText, R.id.priceText, R.id.imageView});

        //Set the adapter to your ListView
        mlistView.setAdapter(adapter);

        mlistView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentFoodDetail = new Intent(Search.this, SearchDetail.class);
                startActivity(intentFoodDetail);

                String selectedFood = mFoodList.get(position).get("id");

                // save user data
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(Search.this);
                SharedPreferences.Editor edit = sp.edit();

                edit.putString("idF", selectedFood);

                edit.commit();

                Toast.makeText(Search.this, "You selected food items " + selectedFood, Toast.LENGTH_LONG).show();

            }
        });
    }

}
