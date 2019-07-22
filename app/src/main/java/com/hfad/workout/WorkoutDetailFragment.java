package com.hfad.workout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// SOS: In the phone version this is hosted by its own activity (DetailActivity). In the tablet
// version this is the 2nd fragment hosted by MainActivity
public class WorkoutDetailFragment extends Fragment {

    private int mWorkoutId;

    void setWorkoutId(int workoutId) {
        mWorkoutId = workoutId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            StopwatchFragment stopwatch = new StopwatchFragment();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.stopwatch_container, stopwatch)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            mWorkoutId = savedInstanceState.getInt("mWorkoutId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        Workout workout = Workout.workouts[mWorkoutId];

        TextView title = view.findViewById(R.id.textTitle);
        title.setText(workout.getName());

        TextView description = view.findViewById(R.id.textDescription);
        description.setText(workout.getDescription());

        return view;
    }

    // SOS: If you follow the code for the tablet, the workoutId is set in handleClickOnTablet only!
    // Which means that after config change, it won't be set at all and will default to 0.
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putLong("mWorkoutId", mWorkoutId);
    }
}
