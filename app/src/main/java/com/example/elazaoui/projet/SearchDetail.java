package com.example.elazaoui.projet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by DUYNGUYEN on 3/14/2016.
 */
public class SearchDetail extends BaseActivity {

    HashMap<String, String> foodItem = new HashMap<String, String>();

    String foodPosition = null;
    String temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("RETURN_MESSAGE",
                        "Hello" + " added to cart");
                setResult(RESULT_OK, data);
                finish();
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

            temp = foodPosition;
        } catch (NumberFormatException e) {
            e.printStackTrace();

            Intent searchIntent = new Intent(SearchDetail.this, Search.class);
            startActivity(searchIntent);
        }


        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap =  getBitmapFromURL(foodItem.get("image"));
        image.setImageBitmap(bitmap);
    }


}
