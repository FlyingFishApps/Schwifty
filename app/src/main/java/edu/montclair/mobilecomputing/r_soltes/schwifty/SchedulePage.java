package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedulePage extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.shiftBtn_FS) Button swapShiftBtn;
    @BindView(R.id.releaseBtnFS) Button releaseShiftBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);
        ButterKnife.bind(this);
        swapShiftBtn.setOnClickListener(this);
        releaseShiftBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.releaseBtnFS:
                startActivity(new Intent(SchedulePage.this, TimeOffPage.class));
                finish();
                break;
            case R.id.shiftBtn_FS:
                startActivity(new Intent(SchedulePage.this, AddEmployeePage.class));
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }
}
