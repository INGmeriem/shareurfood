package com.example.elazaoui.projet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by DUYNGUYEN on 3/9/2016.
 */
public class ShowFood extends ActionBarActivity {

    ListView mListView;
    String[] foods = new String[]{
            "Hamburger", "Pizza", "Coca cola", "Ice tea", "Kebab", "Pho"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfood);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowFood.this, android.R.layout.simple_list_item_1, foods);


    }
}
