package com.hfad.workout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// SOS: Create ListFragments with New->Fragment(Blank), not Fragment(List). The latter adds unnecessary
// boilerplate. Un-tick layout generation, cause ListFragments have their own implicit layout. Finally,
// (obv) don't forget to change the base class to ListFragment.
public class WorkoutListFragment extends ListFragment {

    private Listener mListener;

    // SOS: We want fragments to be reusable, ie to know nothing about the activities that host them.
    // Any activity that wants to host this fragment must simply implement Listener!
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = Workout.workouts[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(),
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

        // SOS: this will return the default view for list-fragments
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // SOS: no need to attach a listener like with ListViews, instead this method will be called.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mListener != null) {
            mListener.itemClicked(id);
        }
    }

    interface Listener {
        void itemClicked(long id);
    }
}
