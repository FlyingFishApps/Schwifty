package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class JobListPage extends AppCompatActivity {

    private List<String> listOfJobs = new ArrayList<>();
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, userRef;
    @BindView(R.id.job_list) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_page);
        ButterKnife.bind(this);

        // Get Firebase user
        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfJobs);

        final String uid = user.getUid().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userRef = mDatabaseReference.child("users");
        userRef.child(uid).child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.getValue().toString();
                    String data2 = data.substring(data.lastIndexOf("=")+1);
                    String data3 = data2.split("\\}")[0];
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(JobListPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
