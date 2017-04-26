package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEmployeePage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ae_business_name) EditText businessNameTxt;
    @BindView(R.id.ae_employee_id) EditText employeeIdTxt;
    @BindView(R.id.ae_add_employee_btn) Button addEmployeeBtn;
    private DatabaseReference mDatabaseReference, businessRef, userIdRef;
    private String employeeId, businessName;
    Snackbar snackbar;
    RelativeLayout activity_add_employee_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee_page);
        ButterKnife.bind(this);

        activity_add_employee_page = (RelativeLayout)findViewById(R.id.activity_add_employee_page);
        addEmployeeBtn.setOnClickListener(this);

    }

    /**
     * Method to listen to the addEmployeeBtn. Switch statement reads the id of the button clicked
     * if bussinessNameTxt is empty, alert user, if not check the business.
     * */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ae_add_employee_btn:

                if(!TextUtils.isEmpty(businessNameTxt.getText().toString())){
                    checkBusiness();

                }else{
                    businessNameTxt.setError("Please enter a business name");
                }
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }



    }

    /**
     * Method to check if a business exists in the database or not.
     * If the business entered by the user does not exist, alert user.
     * Otherwise check the employee;
     * */
    public void checkBusiness(){

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        businessRef = mDatabaseReference.child("businesses");
        businessRef.child(businessNameTxt.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    businessNameTxt.setError("Business does not exist!");
                }else{
                    checkEmployee();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Method to check if the user ID entered by the user is an existing one in the database.
     * If it does not, alert the user. Otherwise add the employee to the business.
     * */
    public void checkEmployee(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        userIdRef = mDatabaseReference.child("users");
        userIdRef.child(employeeIdTxt.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    employeeIdTxt.setError("Employee does not exist!");

                }else{
                    addEmployee();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     *
     * */
    public void addEmployee(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        businessRef = mDatabaseReference.child("businesses");
        businessRef.orderByChild("bName").equalTo(businessNameTxt.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, String> newEmployee = new HashMap();

                newEmployee.put(employeeIdTxt.getText().toString(), employeeIdTxt.getText().toString());

                businessRef.child(businessNameTxt.getText().toString()).child("List Of Employees").push().setValue(newEmployee);


                String data = dataSnapshot.child(businessNameTxt.getText().toString()).child("bName").getValue().toString();
                System.out.println(data);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userIdRef = mDatabaseReference.child("users");
        userIdRef.child(employeeIdTxt.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                userIdRef.child(employeeIdTxt.getText().toString()).child("jobs").push().setValue(businessNameTxt.getText().toString());


                snackbar.make(activity_add_employee_page, "Employee Added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                businessNameTxt.getText().clear();
                employeeIdTxt.getText().clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddEmployeePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
