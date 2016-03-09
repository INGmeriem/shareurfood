package com.example.elazaoui.projet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by DUYNGUYEN on 3/9/2016.
 */
<<<<<<< HEAD
public class Search extends ActionBarActivity {
=======
class ShowFood extends ActionBarActivity {
>>>>>>> 1c7913038ce079fddbff73561d4421635216f581

    ListView mListView;
    String[] foods = new String[]{
            "Hamburger", "Pizza", "Coca cola", "Ice tea", "Kebab", "Pho"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, foods);


    }
}
