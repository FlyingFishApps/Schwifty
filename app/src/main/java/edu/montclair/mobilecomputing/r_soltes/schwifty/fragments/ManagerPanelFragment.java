package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class ManagerPanelFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.create_business_btn) Button createBusinessBtn;
    @BindView(R.id.add_employee_btn) Button addEmployeeBtn;

    public ManagerPanelFragment(){
        // Empty required
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_panel, container, false);

        createBusinessBtn = (Button) view.findViewById(R.id.create_business_btn);
        addEmployeeBtn = (Button) view.findViewById(R.id.add_employee_btn);
        createBusinessBtn.setOnClickListener(this);
        addEmployeeBtn.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View view) {

        Activity parentActivity = getActivity();

        switch (view.getId()) {
            case R.id.create_business_btn:
                Intent intent0 = new Intent(parentActivity, CreateBusinessPage.class);
                ((schwiftyInterface)parentActivity).startMyIntent(intent0);
                break;
            case R.id.add_employee_btn:
                Intent intent1 = new Intent(parentActivity, AddEmployeePage.class);
                ((schwiftyInterface)parentActivity).startMyIntent(intent1);
                break;
            default:
                throw new RuntimeException("Unknown button ID");


        }
    }
}
