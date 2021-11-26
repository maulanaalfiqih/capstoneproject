package com.example.githubtes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.logofix);

        TextView sportsTitle = findViewById(R.id.titleDetail);
        TextView content = findViewById(R.id.newsTitleDetail);
        TextView terbit = findViewById(R.id.subTitleDetail);
        ImageView gambar = findViewById(R.id.sportsImage);

        sportsTitle.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));
        terbit.setText(getIntent().getStringExtra("publishedAt"));
        Glide.with(this).load(getIntent().getStringExtra("urlToImage"))
                .into(gambar);

    }
}