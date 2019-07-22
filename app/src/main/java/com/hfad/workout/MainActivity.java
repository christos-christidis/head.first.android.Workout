package com.hfad.workout;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // SOS: We check the existence of fragment-container to know if we're dealing w a tablet
    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.detail_fragment_container);
        if (fragmentContainer == null) {
            handleClickOnPhone((int) id);
        } else {
            handleClickOnTablet((int) id);
        }
    }

    private void handleClickOnPhone(int id) {
        Intent intent = DetailActivity.newIntent(this, id);
        startActivity(intent);
    }

    // SOS: After this, pressing Back won't exit MainActivity, it'll take us back to previous details.
    private void handleClickOnTablet(int id) {
        WorkoutDetailFragment details = new WorkoutDetailFragment();
        details.setWorkoutId(id);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment_container, details)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null) // don't forget this!
                .commit();
    }
}
