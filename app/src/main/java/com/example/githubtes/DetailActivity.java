package com.example.githubtes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView sportsTitle = findViewById(R.id.titleDetail);
        TextView content = findViewById(R.id.newsTitleDetail);
        TextView terbit = findViewById(R.id.subTitleDetail);

        sportsTitle.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));
        terbit.setText(getIntent().getStringExtra("publishedAt"));


    }
}