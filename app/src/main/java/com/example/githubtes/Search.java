package com.example.githubtes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.githubtes.dataBookmark.BookmarkDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;
    public static BookmarkDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = Room.databaseBuilder(getApplicationContext(),
                BookmarkDatabase.class, "bookmarkDB2").allowMainThreadQueries().build();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.search);

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
                        startActivity(new Intent(getApplicationContext()
                                ,Bookmark.class));
                        overridePendingTransition(0,0);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchbtn);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari berita...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1 ){
                    Log.d("Search", "onQueryTextChange: " + newText);

                    mRecyclerView = findViewById(R.id.recyclerView);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mSportsData = new ArrayList<>();

                    // Initialize the adapter and set it to the RecyclerView.
                    mAdapter = new SportsAdapter(getApplicationContext(), mSportsData);
                    mRecyclerView.setAdapter(mAdapter);
                    getSearching(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getSearching(String newText) {
        final ArrayList<Sport> listNews = new ArrayList<>();
        String baseUrl = "http://newsapi.org/v2/everything?q="+newText+"&apiKey=b386e2145b06466da08c68a57e5eba93";
        AndroidNetworking.get(baseUrl).setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("_kotlinResponse", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject news = jsonArray.getJSONObject(i);
                        Sport itemNews = new Sport();
                        itemNews.setTitle(news.getString("title"));
                        itemNews.setAuthor(news.getString("author"));
                        itemNews.setDescription(news.getString("description"));
                        itemNews.setContent(news.getString("content"));
                        itemNews.setPublishedAt(news.getString("publishedAt"));
                        itemNews.setPhoto(news.getString("urlToImage"));
                        listNews.add(itemNews);
                    }
                    mAdapter.setSportsData(listNews);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
}