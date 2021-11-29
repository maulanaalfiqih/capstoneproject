package com.example.githubtes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.githubtes.dataBookmark.BookmarkDatabase;
import com.example.githubtes.dataBookmark.BookmarkList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BookmarkDatabase db;
    public static List<BookmarkList> mlist;
    private BookmarkAdapter bookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        db = Room.databaseBuilder(getApplicationContext(),
                BookmarkDatabase.class, "bookmarkDB2").allowMainThreadQueries().build();

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.logofix);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.Bookmark);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                ,Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.NightMode:

                        // Get the night mode state of the app.
                        int nightMode = AppCompatDelegate.getDefaultNightMode();
                        //Set the theme mode for the restarted activity
                        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode
                                    (AppCompatDelegate.MODE_NIGHT_NO);
                        } else {
                            AppCompatDelegate.setDefaultNightMode
                                    (AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        // Recreate the activity for the theme change to take effect.
                        recreate();
                        return true;
                    case R.id.Bookmark:
                        return true;
                    case R.id.info:
                        startActivity(new Intent(getApplicationContext()
                                ,Info.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mlist = db.bookmarkDao().selectAllBookmark();

        bookmarkAdapter = new BookmarkAdapter(getApplicationContext(), mlist);
        mRecyclerView.setAdapter(bookmarkAdapter);

    }
}