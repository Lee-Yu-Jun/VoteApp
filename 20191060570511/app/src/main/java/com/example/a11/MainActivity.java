package com.example.a11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static int voteCount[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voteCount = new int[9];
        for(int i  = 0; i < 9; i++)voteCount[i] = 0;

        ImageView image[] = new ImageView[9];

        Integer imageId[] = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9,};

        final String imgName[] = {"왁두1", "왁두2", "왁두3", "왁두4", "왁두5", "왁두6", "왁두7", "왁두8", "왁두9"};

        for (int i = 0; i < imageId.length; i++) {
            final int index;
            index = i;
            image[index] = (ImageView) findViewById(imageId[index]);
            image[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    voteCount[index]++;
                    Toast.makeText(getApplicationContext(),
                            imgName[index] + ": 총"  + voteCount[index] + "표",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        Button btnFinish = (Button) findViewById(R.id.btnresult);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("ImageName", imgName);
                startActivity(intent);
            }
        });
    }
}