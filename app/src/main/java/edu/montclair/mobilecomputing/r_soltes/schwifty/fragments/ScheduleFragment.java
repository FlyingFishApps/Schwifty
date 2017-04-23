package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import edu.montclair.mobilecomputing.r_soltes.schwifty.AddEmployeePage;
import edu.montclair.mobilecomputing.r_soltes.schwifty.CreateBusinessPage;
import edu.montclair.mobilecomputing.r_soltes.schwifty.R;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.schwiftyInterface;


public class ScheduleFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.shiftBtn_FS) Button swapShiftBtn;
    @BindView(R.id.releaseBtnFS) Button releaseShiftBtn;

    public ScheduleFragment(){
        //Empty required
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        swapShiftBtn = (Button)view.findViewById(R.id.shiftBtn_FS);
        releaseShiftBtn = (Button)view.findViewById(R.id.releaseBtnFS);
        swapShiftBtn.setOnClickListener(this);
        releaseShiftBtn.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {

        Activity parentActivity = getActivity();

        switch (view.getId()) {
            case R.id.releaseBtnFS:
                Intent intent = new Intent(parentActivity, TimeOffFragment.class);
                ((schwiftyInterface)parentActivity).startMyIntent(intent);
                break;
            case R.id.shiftBtn_FS:
                Intent intent1 = new Intent(parentActivity, AddEmployeePage.class);
                ((schwiftyInterface)parentActivity).startMyIntent(intent1);
                break;
            default:
                throw new RuntimeException("Unknown button ID");


        }
    }
}
