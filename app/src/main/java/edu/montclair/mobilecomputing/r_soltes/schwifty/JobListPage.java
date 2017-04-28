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

    @BindView(R.id.job_list) ListView mListView;

    private List<String> listOfJobs = new ArrayList<>();
    private DatabaseReference mDatabaseReference, userRef;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_page);

        // Bind views
        ButterKnife.bind(this);

        // Get Firebase user
        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        // Create array adapter with a simple list item layout for listOfJobs
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfJobs);
        // Create string from current user's user ID
        final String uid = user.getUid().toString();
        // Get reference to database
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        // Get reference to users table in database
        userRef = mDatabaseReference.child("users");
        // Add listener to the jobs child of the current user
        userRef.child(uid).child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // Add each job in a user's jobs child to the array
                // Set the array adapter to the list view on UI to display elements in array
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

    /**
     * When back button is pressed redirects to home page.
     * **/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(JobListPage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
