package com.enihsyou.androidexamination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.enihsyou.androidexamination.dao.MovieSearchDatabaseHelper;

public class SqliteActivity extends AppCompatActivity {
private String TAG = "生命周期";
    private MovieSearchDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        helper = new MovieSearchDatabaseHelper(this);

        Button insert = findViewById(R.id.button1);
        Button update = findViewById(R.id.button2);
        Button select = findViewById(R.id.button3);
        Button delete = findViewById(R.id.button4);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                helper.insertRecord("AAA");
                showResult();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                helper.updateRecords("AAA", "BBB");
                showResult();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                helper.getRecords();
                showResult();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                helper.deleteRecord("AAA");
                showResult();
            }
        });
        Log.i(TAG, "调用onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "调用onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "调用onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "调用onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "调用onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "调用onStart");
    }

    private void showResult() {
        Toast.makeText(this, "数据库中: " + helper.getRecords(), Toast.LENGTH_SHORT).show();
    }
}
