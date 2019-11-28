package com.example.foodmemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FoodSearchForm extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter;
    ArrayList<FoodList> data = null;
    Cursor cursor;
    FoodList foodList;

    DBHelper mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_searchform);



        listView = (ListView)findViewById(R.id.foodListview);

        data = new ArrayList<FoodList>();
        mydb = new DBHelper(getApplicationContext());
        adapter = null;
        cursor = null;
        cursor = mydb.getCursorFood();
        while (cursor.moveToNext()) {
            foodList = new FoodList(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("name")),
            cursor.getInt(cursor.getColumnIndex("type")),
            cursor.getInt(cursor.getColumnIndex("score")),
            cursor.getInt(cursor.getColumnIndex("region")),
            cursor.getString(cursor.getColumnIndex("phone")),
            cursor.getString(cursor.getColumnIndex("address")),
            cursor.getString(cursor.getColumnIndex("memo")),
            cursor.getString(cursor.getColumnIndex("pic"))
            );
            data.add(foodList);
        } cursor.close();


        adapter = new MyAdapter(getApplicationContext(),R.layout.listviewform,data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long arg4) {

                Intent intent = new Intent(getApplicationContext(),FoodDsForm.class);
                intent.putExtra("id",data.get(position).getId());
                intent.putExtra("name",data.get(position).getName());
                intent.putExtra("type",data.get(position).getType());
                intent.putExtra("score",data.get(position).getScore());
                intent.putExtra("region",data.get(position).getRegion());
                intent.putExtra("phone",data.get(position).getPhone());
                intent.putExtra("address",data.get(position).getAddress());
                intent.putExtra("memo",data.get(position).getMemo());
                intent.putExtra("pic",data.get(position).getPic());

                startActivity(intent);
                finish();
            }
        });
    }
}