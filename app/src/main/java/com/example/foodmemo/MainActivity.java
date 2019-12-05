package com.example.foodmemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton homeAddButton;
    ImageButton homeSearchButton;
    TextView main_count;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(getApplicationContext());

        main_count = (TextView)findViewById(R.id.main_count);
        main_count.setText(mydb.numberOfRows()+"");

        homeAddButton = (ImageButton)findViewById(R.id.homeAddButton);
        homeAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FoodDsForm.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });
        homeSearchButton = (ImageButton)findViewById(R.id.homeSearchButton);
        homeSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FoodSearchForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        main_count.setText(mydb.numberOfRows()+"");
    }
}