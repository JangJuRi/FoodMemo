package com.example.foodmemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

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

    int score_value;
    String file_path = null;
    Uri uri;
    byte[] logoImage;
    Bitmap img;
    Cursor cursor;

    private final int GET_PICTURE = 200;


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

        food_pic.setOnClickListener(this);
        food_edit.setOnClickListener(this);
        food_del.setOnClickListener(this);
        food_save.setOnClickListener(this);

        ButtonVisible();    //저장,수정버튼 활성화

        mydb = new DBHelper(getApplicationContext());
        Intent intent2 = getIntent();
        if(intent2 != null) {
            int value = intent2.getIntExtra("id",0);

            if(value > 0) {
                Intent intent = getIntent();

                food_name.setText(intent.getStringExtra("name"));
                food_type.setSelection((intent.getIntExtra("type",1)));
                food_score.setRating((intent.getIntExtra("score",5)));
                food_region.setSelection((intent.getIntExtra("region",1)));
                food_phone.setText(intent.getStringExtra("phone"));
                food_address.setText(intent.getStringExtra("address"));
                food_memo.setText(intent.getStringExtra("memo"));

                cursor = null;
                cursor = mydb.getPic(value);
                while(cursor.moveToNext()) {
                    logoImage = cursor.getBlob(cursor.getColumnIndex("pic"));
                }cursor.close();
                if(logoImage != null) food_pic.setImageBitmap(getImage(logoImage));
                score_value = intent.getIntExtra("score",5);
            }
        }

        food_score.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score_value = (int) rating;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_save :
                    Intent intent = getIntent();
                    int Value = intent.getIntExtra("id",0);
                    id = Value;
                    if(Value > 0) {
                        if(mydb.updateFood(id,food_name.getText().toString(),food_type.getSelectedItemPosition(),food_type.getSelectedItem().toString(),score_value,food_region.getSelectedItemPosition(), food_region.getSelectedItem().toString(),food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),logoImage)) {
                            Toast.makeText(getApplicationContext(), "수정 완료!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "수정 실패",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    } else {
                        if (mydb.insertFood(food_name.getText().toString(),food_type.getSelectedItemPosition(),food_type.getSelectedItem().toString(),score_value,food_region.getSelectedItemPosition(), food_region.getSelectedItem().toString(),food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),logoImage)) {
                            Toast.makeText(getApplicationContext(), "추가 완료!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "추가 실패",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                break;

            case R.id.food_del:
                Intent intent2 = getIntent();
                int value = intent2.getIntExtra("id",0);
                    id = value;
                    if(value > 0) {
                        mydb.deleteFood(id);
                        Toast.makeText(getApplicationContext(), "삭제 성공!",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "삭제 실패",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                break;

            case R.id.food_edit:
                Intent intent3 = getIntent();
                int Value3 = intent3.getIntExtra("id",0);
                     id = Value3;
                    if(Value3 > 0) {
                        if(mydb.updateFood(id,food_name.getText().toString(),food_type.getSelectedItemPosition(), food_type.getSelectedItem().toString(),score_value,food_region.getSelectedItemPosition(), food_region.getSelectedItem().toString(),food_phone.getText().toString(),food_address.getText().toString(),food_memo.getText().toString(),logoImage)) {
                            Toast.makeText(getApplicationContext(), "수정 완료!",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "수정 실패",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                break;

            case R.id.food_pic:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(i,GET_PICTURE);
        }
    }

    public void ButtonVisible() {
        Intent intent = getIntent();
        int Value = intent.getIntExtra("id",0);
        id = Value;

        if(Value > 0) {     //수정버튼 활성화
            food_edit.setVisibility(View.VISIBLE);
            food_save.setVisibility(View.GONE);
            food_del.setVisibility(View.VISIBLE);
        }
        else {              //추가버튼 활성화
            food_edit.setVisibility(View.GONE);
            food_save.setVisibility(View.VISIBLE);
            food_del.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try{
                InputStream in = getContentResolver().openInputStream(data.getData());

                img = BitmapFactory.decodeStream(in);
                in.close();
                logoImage = getBytes(img);
                food_pic.setImageBitmap(img);
            }catch(Exception e) { }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
        }
//                uri = data.getData();
//                logoImage = getLogoImage(String.valueOf(uri));
//                food_pic.setImageURI(uri);
//                Log.e("test", String.valueOf(logoImage));
        }

        private byte[] getBytes(Bitmap bitmap) {        //비트맵을 바이트로
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

         private Bitmap getImage(byte[] image) {        //바이트를 비트맵으로
        return BitmapFactory.decodeByteArray(image, 0, image.length);
       }
}