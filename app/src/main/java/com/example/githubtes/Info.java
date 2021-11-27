package com.example.githubtes;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class Info extends AppCompatActivity {

    SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        sliderLayout = findViewById(R.id.our_team);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(1);

        setSliderViews();

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.logofix);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.info);

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
                        startActivity(new Intent(getApplicationContext()
                                ,Bookmark.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.info:
                        return true;
                }

                return false;
            }
        });
    }

    private  void setSliderViews() {

        for (int i = 0; i <=3; i++){

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.fahlevi);
                    sliderView.setDescription("Fahlevi Dwi Yauma H 09031281924159");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.maul);
                    sliderView.setDescription("M. Maulana Alfiqih A 09031281924073");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.lutfi);
                    sliderView.setDescription("Muhammad Lutfi 09031181924007");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.ilyas);
                    sliderView.setDescription("M. Ilyas Kesuma 09031281924031");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView){
                    Toast.makeText(Info.this, "This is slider" + (finalI + 1), Toast.LENGTH_SHORT);
                }
            });

            sliderLayout.addSliderView(sliderView);
        }
    }
}