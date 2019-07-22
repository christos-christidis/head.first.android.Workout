package com.hfad.workout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

// This activity is only used in the phone version
public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_WORKOUT_ID = "extra_workout_id";

    static Intent newIntent(Activity activity, int id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // SOS: Every time DetailActivity is recreated, it gets the workoutId from the intent which
        // persists and passes it to the fragment (thus no need for onSaveInstanceState).
        int workoutId = getIntent().getIntExtra(EXTRA_WORKOUT_ID, -1);
        if (workoutId == -1) {
            Toast.makeText(this, "Can't get EXTRA_WORKOUT_ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // SOS: The fragment gets its mWorkoutId set here in onCreate, it then uses that id in its
        // onCreateView which runs AFTER onCreate here, so all is well.
        WorkoutDetailFragment fragment = (WorkoutDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment);
        if (fragment != null) {
            fragment.setWorkoutId(workoutId);
        }
    }
}
