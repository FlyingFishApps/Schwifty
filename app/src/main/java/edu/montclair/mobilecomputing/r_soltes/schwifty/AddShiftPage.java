package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * Created by tjame_000 on 4/25/2017.
 */

public class AddShiftPage extends AppCompatActivity implements View.OnClickListener {

    EditText txtDate, txtTime, endTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button cShiftBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shift);
        ButterKnife.bind(this);

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        endTime=(EditText)findViewById(R.id.end_time);
        cShiftBtn=(Button)findViewById(R.id.create_shift_btn) ;


        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        cShiftBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == txtDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == txtTime) {

            // Get Current Time

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();


        }
        if (v == endTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();


        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddShiftPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}