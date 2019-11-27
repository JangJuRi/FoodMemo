package com.example.foodmemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDsForm extends AppCompatActivity implements View.OnClickListener{

    private DBHelper mydb;
    int id = 0;

    TextView food_name;
    Spinner food_type;
    RatingBar food_score;
    Spinner food_region;
    TextView food_phone;
    TextView food_address;
    TextView food_memo;
    ImageView food_pic;

    Button food_edit;
    Button food_del;
    Button food_save;

    int type_value;
    int score_value;
    int region_value;
    String pic_value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_dsform);

        food_name = (TextView)findViewById(R.id.food_name);
        food_type = (Spinner)findViewById(R.id.food_type);
        food_score = (RatingBar)findViewById(R.id.food_score);
        food_region = (Spinner) findViewById(R.id.food_region);
        food_phone = (TextView)findViewById(R.id.food_phone);
        food_address = (TextView)findViewById(R.id.food_address);
        food_memo = (TextView)findViewById(R.id.food_memo);
        food_pic = (ImageView)findViewById(R.id.food_pic);

        food_edit = (Button)findViewById(R.id.food_edit);
        food_del = (Button)findViewById(R.id.food_del);
        food_save = (Button)findViewById(R.id.food_save);

        food_edit.setOnClickListener(this);
        food_del.setOnClickListener(this);
        food_save.setOnClickListener(this);

        mydb = new DBHelper(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int value = extras.getInt("id");

            if(value > 0) {
                Intent intent = getIntent();

                food_name.setText(intent.getStringExtra("name"));
                food_type.setSelection(Integer.parseInt(intent.getStringExtra("type")));
                food_score.setRating(Float.parseFloat(intent.getStringExtra("score")));
                food_region.setSelection(Integer.parseInt(intent.getStringExtra("region")));
                food_phone.setText(intent.getStringExtra("phone"));
                food_address.setText(intent.getStringExtra("address"));
                food_memo.setText(intent.getStringExtra("memo"));
                pic_value = intent.getStringExtra("pic");
                food_pic.setImageResource(Integer.parseInt(pic_value));
            }
        }

        food_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_value = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        food_score.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score_value = (int) rating;
            }
        });

        food_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_save :
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    int Value = extras.getInt("id");
                    if(Value > 0) {
                        if(mydb.updateFood(id,food_name.getText().toString(),type_value,score_value,region_value,food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),pic_value)) {
                            Toast.makeText(getApplicationContext(), "수정 완료!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "수정 실패",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (mydb.insertFood(food_name.getText().toString(),type_value,score_value,region_value,food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),pic_value)) {
                            Toast.makeText(getApplicationContext(), "추가 완료!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "추가 실패",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                }
                else Toast.makeText(getApplicationContext(), "오류",Toast.LENGTH_SHORT).show();
                break;

            case R.id.food_del:
                Bundle extras2 = getIntent().getExtras();
                if(extras2 != null) {
                    int value = extras2.getInt("id");
                    if(value > 0) {
                        mydb.deleteFood(id);
                        Toast.makeText(getApplicationContext(), "삭제 성공!",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "삭제 실패",Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getApplicationContext(), "오류",Toast.LENGTH_SHORT).show();
                break;

            case R.id.food_edit:
                Bundle extras3 = getIntent().getExtras();
                if(extras3 != null) {
                    int Value = extras3.getInt("id");
                    if(Value > 0) {
                        if(mydb.updateFood(id,food_name.getText().toString(),type_value,score_value,region_value,food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),pic_value)) {
                            Toast.makeText(getApplicationContext(), "수정 완료!",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "수정 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(getApplicationContext(), "오류",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}