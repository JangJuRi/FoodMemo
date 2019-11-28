package com.example.foodmemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button homeAddButton;
    Button homeSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeAddButton = (Button)findViewById(R.id.homeAddButton);
        homeAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FoodDsForm.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });
        homeSearchButton = (Button)findViewById(R.id.homeSearchButton);
        homeSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FoodSearchForm.class);
                startActivity(intent);
            }
        });
    }
}