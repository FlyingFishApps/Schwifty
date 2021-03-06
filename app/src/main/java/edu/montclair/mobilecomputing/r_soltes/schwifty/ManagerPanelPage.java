package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerPanelPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.create_business_btn) Button createBusinessBtn;
    @BindView(R.id.add_employee_btn) Button addEmployeeBtn;
    @BindView(R.id.noti_manger_btn) Button manageNotiBtn;
    @BindView(R.id.add_shift_btn) Button aShiftBtn;
    @BindView(R.id.noti_swap_btn_MP) Button aSwapBtn;
    @BindView(R.id.noti_sch_btn_MP) Button aSchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_panel_page);
        ButterKnife.bind(this);
        createBusinessBtn.setOnClickListener(this);
        addEmployeeBtn.setOnClickListener(this);
        manageNotiBtn.setOnClickListener(this);
        aShiftBtn.setOnClickListener(this);
        aSwapBtn.setOnClickListener(this);
        aSchBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.create_business_btn:
                startActivity(new Intent(ManagerPanelPage.this, CreateBusinessPage.class));
                finish();
                break;
            case R.id.add_employee_btn:
                startActivity(new Intent(ManagerPanelPage.this, AddEmployeePage.class));
                finish();
                break;
            case R.id.noti_manger_btn:
                startActivity(new Intent(ManagerPanelPage.this, ManagerNotificationPage.class));
                finish();
                break;
            case R.id.add_shift_btn:
                startActivity(new Intent(ManagerPanelPage.this,AddShiftPage.class));
                finish();
                break;
            case R.id.noti_swap_btn_MP:
                startActivity(new Intent(ManagerPanelPage.this,SwapPage.class));
                finish();
                break;
            case R.id.noti_sch_btn_MP:
                startActivity(new Intent(ManagerPanelPage.this,SchedulePage.class));
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManagerPanelPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
