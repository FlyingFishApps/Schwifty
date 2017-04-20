package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.Business;

public class CreateBusinessPage extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.cb_business_name) EditText businessName;
    @BindView(R.id.cb_create_btn) Button createBusinessBtn;
    @BindView(R.id.cbProgressBar) ProgressBar mprogressBar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    Snackbar snackbar;
    RelativeLayout activity_create_business_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_page);
        ButterKnife.bind(this);

        createBusinessBtn.setOnClickListener(this);
        activity_create_business_page = (RelativeLayout)findViewById(R.id.create_business_page);

        // Firebase Init
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_create_btn:
                if(!TextUtils.isEmpty(businessName.getText().toString().trim())){
//                    createBusiness(businessName.getText().toString().trim());
                    checkBusiness();
                    snackbar.make(activity_create_business_page, "Business Created!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    businessName.setError("Please enter a business name");
                }
                break;
            default:
                throw new RuntimeException("Unknown button ID");


        }
    }

    private void createBusiness(String name) {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String bId = String.valueOf(n);

        mprogressBar.setVisibility(View.VISIBLE);

        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        String bOwner = user.getUid().toString();
        Business business = new Business(name,bId, bOwner);
        mDatabaseReference.child("businesses").child(name).setValue(business);

        HashMap<String, String> employees = new HashMap();

        employees.put(user.getUid(), user.getUid());

        mDatabaseReference.child("businesses").child(name).child("List Of Employees").setValue(employees);

        businessName.getText().clear();
        mprogressBar.setVisibility(View.GONE);

    }

    private void checkBusiness(){

        mDatabaseReference.child("businesses").child(businessName.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    businessName.getText().clear();
                    businessName.setError("Business already exists.");
                }else{
                    createBusiness(businessName.getText().toString().trim());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
