package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedulePage extends AppCompatActivity  {

    @BindView(R.id.job_list1)
    ListView mListView;
    private DatabaseReference mDatabaseReference, notifRef, notifRefJ, userIdRefn, userIdRefID, userIdRef,userBusRef, businessRef;
    private FirebaseAuth mFirebaseAuth;
    private String value;
    private List<String> listOfJobs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);
        ButterKnife.bind(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfJobs);

        notifRef = mDatabaseReference.child("businesses");
        notifRefJ = mDatabaseReference.child("usersIDs");
        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user3 = mFirebaseAuth.getInstance().getCurrentUser();
        final String uid = user3.getUid().toString();

        notifRefJ.child(uid).child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // Add each job in a user's jobs child to the array
                // Set the array adapter to the list view on UI to display elements in array
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.getValue().toString();
                    String data2 = data.substring(data.lastIndexOf("=")+1);
                    final String data3 = data2.split("\\}")[0];

                    value = data3;
                    notifRef.child(value).child("full_schedule_noti").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            // Add each job in a user's jobs child to the array
                            // Set the array adapter to the list view on UI to display elements in array
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue().toString();
                                String data2 = data.substring(data.lastIndexOf("=")+1);
                                final String data3 = data2.split("\\}")[0];


                                System.out.println(data3);
                                listOfJobs.add(data3);
                                mListView.setAdapter(arrayAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SchedulePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
