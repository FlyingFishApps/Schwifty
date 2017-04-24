package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilePage extends AppCompatActivity {

    @BindView(R.id.prof_username) TextView usernameTv;
    @BindView(R.id.prof_email) TextView emailTv;
    @BindView(R.id.prof_userID) TextView userIdTv;
    @BindView(R.id.prof_userRole) TextView userRoleTv;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ButterKnife.bind(this);

        // Get Firebase user
        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

        final String uid = user.getUid().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userRef = mDatabaseReference.child("users");
        userRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String username = dataSnapshot.child(uid).child("username").getValue().toString();
                String email = dataSnapshot.child(uid).child("email").getValue().toString();
                String userId = dataSnapshot.child(uid).child("uid").getValue().toString();
                String userRole = dataSnapshot.child(uid).child("userRole").getValue().toString();
                usernameTv.append(" "+username);
                emailTv.append(" "+email);
                userIdTv.append(" "+userId);
                userRoleTv.append(" "+userRole);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfilePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
