package com.example.a11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    static int[] voteResult;
    static TextView tv[];
    static RatingBar rbar[];
    static String[] imageName;
    DBHelper dbHelper;
    SQLiteDatabase db;
    static boolean loadFlag = false;
    boolean insertFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        voteResult = MainActivity.voteCount;
        imageName = intent.getStringArrayExtra("ImageName");

        if(loadFlag==false){
            String sql = " SELECT * FROM " + DBHelper.FeedEntry.TABLE_NAME;
            Cursor c = db.rawQuery(sql, null);
            if(c.getCount()>0){
                while(c.moveToNext()){
                    @SuppressLint("Range") String tmp1 = c.getString(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_NAME));
                    @SuppressLint("Range") int tmp2 = c.getInt(c.getColumnIndex(DBHelper.FeedEntry.COLUMN_NAME_NAME));

                    for(int i = 0; i < imageName.length; i++){
                        if(tmp1.equals(imageName[i])){
                            voteResult[i] += tmp2;
                            break;
                        }

                    }
                }
            } else {
                insertFlag = true;
            }
            loadFlag = true;
        }
        tv = new TextView[imageName.length];
        rbar = new RatingBar[imageName.length];

        Integer tvID[] = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9};

        Integer rbarID[] = {R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4, R.id.rbar5,R.id.rbar6,R.id.rbar7,R.id.rbar8,R.id.rbar9};

        for(int i = 0; i < voteResult.length; i++){
            tv[i] = (TextView) findViewById(tvID[i]);
            rbar[i] = (RatingBar) findViewById(rbarID[i]);
        }

        for(int i = 0; i < voteResult.length; i++){
            tv[i].setText(imageName[i]);
            rbar[i].setRating((float) voteResult[i]);
        }
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        Button btnclear = (Button) findViewById(R.id.btnclear);
        Button btnsave = (Button) findViewById(R.id.btnsave);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < voteResult.length; i++) {
                    rbar[i].setRating(0);
                    MainActivity.voteCount[i] = 0;
                    String sql = " update " + DBHelper.FeedEntry.TABLE_NAME + " set " + DBHelper.FeedEntry.COLUMN_NAME_NUMBER + " = 0 where 1 ";
                    db.execSQL(sql);
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < voteResult.length; i++){
                    String sql;
                    if(insertFlag==false) sql = " update " + DBHelper.FeedEntry.TABLE_NAME + " set " + DBHelper.FeedEntry.COLUMN_NAME_NUMBER + " = "
                            + voteResult[i] + " where " + DBHelper.FeedEntry.COLUMN_NAME_NAME + " = " + " ' " + imageName[i] +  " ' ";
                    else sql = " insert into " + DBHelper.FeedEntry.TABLE_NAME +  " ( " + DBHelper.FeedEntry.COLUMN_NAME_NUMBER + " , " + DBHelper.FeedEntry.COLUMN_NAME_NUMBER
                            + " ) values ( " + " ' " + imageName[i] + " ', " + voteResult[i] + " ) ";
                    Log.d(sql, "onClick: ");
                    db.execSQL(sql);
                }
                insertFlag = false;
            }
        });

    }
}

