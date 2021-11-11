package com.example.githubtes;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Main Activity for the Material Me app, a mock sports news application.
 */
public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        //setFilter to Search item
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                newText = newText.toLowerCase();
//                ArrayList<Sport> itemFilter = new ArrayList<>();
//                for (Sport model : mSportsData) {
//                    String nama = model.getTitle().toLowerCase();
//                    if (nama.contains(newText)){
//                        itemFilter.add(model);
//                    }
//                }
//                mAdapter.setFilter(itemFilter);
//                return true;
//            }
//        });
        // Change the label of the menu based on the state of the app.
       int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
          menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
       } else{
          menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
      }
       return super.onCreateOptionsMenu(menu);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Check if the correct item was clicked
        if(item.getItemId()==R.id.night_mode){}
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int gridColumnCount =
                getResources().getInteger(R.integer.grid_column_count);
        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |
                                                                     ItemTouchHelper.RIGHT |
                                                                     ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                                                                     swipeDirs) {
                                                                 /**
                                                                  * Defines the drag and drop functionality.
                                                                  *
                                                                  * @param recyclerView The RecyclerView that contains the list items
                                                                  * @param viewHolder The SportsViewHolder that is being moved
                                                                  * @param target The SportsViewHolder that you are switching the
                                                                  *               original one with.
                                                                  * @return true if the item was moved, false otherwise
                                                                  */
                                                                 @Override
                                                                 public boolean onMove(RecyclerView recyclerView,
                                                                                       RecyclerView.ViewHolder viewHolder,
                                                                                       RecyclerView.ViewHolder target) {
                                                                     // Get the from and to positions.
                                                                     int from = viewHolder.getAdapterPosition();
                                                                     int to = target.getAdapterPosition();

                                                                     // Swap the items and notify the adapter.
                                                                     Collections.swap(mSportsData, from, to);
                                                                     mAdapter.notifyItemMoved(from, to);
                                                                     return true;
                                                                 }

                                                                 /**
                                                                  * Defines the swipe to dismiss functionality.
                                                                  *
                                                                  * @param viewHolder The viewholder being swiped.
                                                                  * @param direction The direction it is swiped in.
                                                                  */
                                                                 @Override
                                                                 public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                                                                      int direction) {
                                                                     // Remove the item from the dataset.
                                                                     mSportsData.remove(viewHolder.getAdapterPosition());
                                                                     // Notify the adapter.
                                                                     mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                                                 }
                                                             });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);


    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] sportsList = getResources()
                .getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources()
                .getStringArray(R.array.sports_info);
        TypedArray sportsImageResources = getResources()
                .obtainTypedArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        mSportsData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        sportsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    /**
     * onClick method for th FAB that resets the data.
     *
     * @param view The button view that was clicked.
     */
    public void resetSports(View view) {
        initializeData();
    }
//    private void getNews() {
//        final ArrayList<News> listNews = new ArrayList<>();
//
//        String baseUrl = "http://newsapi.org/v2/top-headlines?country=id&category=technology&apiKey=b386e2145b06466da08c68a57e5eba93";
//        AndroidNetworking.get(baseUrl)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("_kotlinResponse", response.toString());
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("articles");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject news = jsonArray.getJSONObject(i);
//                                News itemNews = new News();
//                                itemNews.setTitle(news.getString("author"));
//                                itemNews.setDescription(news.getString("description"));
//                                itemNews.setPhoto(news.getString("urlToImage"));
//                                listNews.add(itemNews);
//                            }
//                            adapter.setData(listNews);
//                            showLoading(false);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.d("onFailure", anError.getMessage());
//                    }
//                });
//    }
}
