package com.example.elazaoui.projet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by DUYNGUYEN on 3/14/2016.
 */
public class SearchDetail extends BaseActivity {

    HashMap<String, String> foodItem = new HashMap<String, String>();

    String foodPosition = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                Intent data = new Intent();
                                data.putExtra(Search.RETURN_MESSAGE,
                                        "\"" + foodItem.get("name") + "\"" + " added to cart");
                                setResult(RESULT_OK, data);
                                finish();

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

/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
*/


}
