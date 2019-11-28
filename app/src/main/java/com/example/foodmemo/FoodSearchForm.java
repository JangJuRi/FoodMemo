package com.example.foodmemo;

import android.content.Intent;
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
    ArrayList<FoodList> Food_list = new ArrayList<FoodList>();
    ArrayList<FoodList> data = null;

    DBHelper mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_searchform);

        listView = (ListView)findViewById(R.id.foodListview);

        data = new ArrayList<>();
        mydb = new DBHelper(getApplicationContext());
        adapter = null;

        data.add(new FoodList(1,"이름222",1,3,2,"010222111","주소","메모","ss"));
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