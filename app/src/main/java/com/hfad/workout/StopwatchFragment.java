package com.hfad.workout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

// SOS: The conversion StopwatchActivity -> StopwatchFragment requires attaching button listeners
// manually instead of using android:onClick, cause that will throw an exception (works only w activity)
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private int mTenthsOfSecond = 0;
    private boolean mRunning = false;
    private boolean mWasRunning = false;

    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTenthsOfSecond = savedInstanceState.getInt("mTenthsOfSecond");
            mWasRunning = savedInstanceState.getBoolean("mWasRunning");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        runTimer(view);

        Button startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = view.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt("mTenthsOfSecond", mTenthsOfSecond);
        savedInstanceState.putBoolean("mWasRunning", mWasRunning);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWasRunning) {
            mRunning = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mRunning = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void runTimer(View view) {
        final TextView timeView = view.findViewById(R.id.time_view);

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = mTenthsOfSecond / 36000;
                int minutes = (mTenthsOfSecond % 36000) / 600;
                int secs = (mTenthsOfSecond % 600) / 10;
                int fraction = mTenthsOfSecond % 10;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d.%d", hours, minutes, secs, fraction);
                timeView.setText(time);
                if (mRunning) {
                    mTenthsOfSecond++;
                }
                handler.postDelayed(this, 100);
            }
        });
    }

    private void onClickStart() {
        mRunning = true;
        mWasRunning = true;
    }

    private void onClickStop() {
        mRunning = false;
        mWasRunning = false;
    }

    private void onClickReset() {
        mRunning = false;
        mWasRunning = false;
        mTenthsOfSecond = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onClickStart();
                break;
            case R.id.stop_button:
                onClickStop();
                break;
            case R.id.reset_button:
                onClickReset();
                break;
        }
    }
}
