package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;


public class TimeOffFragment extends Fragment {

    public TimeOffFragment(){
        // Empty Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_off, container, false);
        return view;
    }

}
