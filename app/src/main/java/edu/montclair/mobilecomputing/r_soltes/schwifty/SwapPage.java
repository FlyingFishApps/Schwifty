package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tjame_000 on 4/27/2017.
 */

public class SwapPage extends AppCompatActivity {
    @BindView(R.id.emp_schwiftname_1)EditText emP1;
    @BindView(R.id.emp_schwiftname_2)EditText emP2;
    @BindView(R.id.emp_sID_1)EditText sID1;
    @BindView(R.id.emp_sID_2)EditText sID2;
    @BindView(R.id.emp_workplace)EditText work;
    @BindView(R.id.schwift_btn)Button sBtn;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mData, mData1, mDataq;
    private boolean reason, reason1;
    private String torn, torn2;

    RelativeLayout activity_schwift_shift_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schwift_shift_page);
        ButterKnife.bind(this);
        activity_schwift_shift_page = (RelativeLayout)findViewById(R.id.activity_schwift_shift_page);

        mData = FirebaseDatabase.getInstance().getReference().child("users");
        mDataq = FirebaseDatabase.getInstance().getReference().child("businesses").child(work.getText().toString());
        mData1 = FirebaseDatabase.getInstance().getReference().child("users");

        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user1 = mFirebaseAuth.getInstance().getCurrentUser();

                // Creates string user id from current user's user ID
                moveFirebaseRecord();
            }
        });
    }

    public void moveFirebaseRecord() {


            mData.child(emP1.getText().toString()).child("Schedule").child("Shift: " + sID1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mData1.child(emP2.getText().toString()).child("Schedule").child("Shift: " + sID1.getText().toString()).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {


                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            mDataq.child("full_schedule_noti").child(sID1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value = (String) dataSnapshot.getValue();
                                    System.out.println(value);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        moveFirebaseRecord2();
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });

    }


    public void removeFirebaseRecord(){
        mData.child(emP1.getText().toString()).child("Schedule").child("Shift: "+sID1.getText().toString()).removeValue();
        mData.child(emP2.getText().toString()).child("Schedule").child("Shift: "+sID2.getText().toString()).removeValue();

        sID1.getText().clear();
        sID2.getText().clear();
        emP2.getText().clear();
        emP1.getText().clear();
        work.getText().clear();
        Snackbar.make(activity_schwift_shift_page, "Shift Added!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public void moveFirebaseRecord2(){
        mData.child(emP2.getText().toString()).child("Schedule").child("Shift: "+sID2.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mData1.child(emP1.getText().toString()).child("Schedule").child("Shift: "+sID2.getText().toString()).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener(){

                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mDataq.child("full_schedule_noti").child(sID2.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                torn2 = dataSnapshot.getValue().toString();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        removeFirebaseRecord();
//                        swapNotifications();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError firebaseError){

            }
        });
    }
    
    private void swapNotifications(){

        mDataq.child("full_schedule_noti").child(sID2.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataq.setValue(torn);
//                swapNotifications2();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void swapNotifications2(){

        mDataq.child("full_schedule_noti").child(sID1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataq.setValue(torn2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SwapPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
