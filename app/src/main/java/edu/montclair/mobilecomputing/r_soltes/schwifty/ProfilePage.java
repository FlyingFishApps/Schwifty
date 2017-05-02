package edu.montclair.mobilecomputing.r_soltes.schwifty;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class ProfilePage extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.prof_username) TextView usernameTv;
    @BindView(R.id.prof_email) TextView emailTv;
    @BindView(R.id.prof_userID) TextView userIdTv;
    @BindView(R.id.prof_userRole) TextView userRoleTv;
    @BindView(R.id.noti_button_PP) Button shiftBtn;
    @BindView(R.id.noti_copy_uid_PP) Button copyBtn;

    private String copyUID;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    TextView textView;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ButterKnife.bind(this);
        shiftBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        // Get Firebase user
        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

        final String uid = user.getUid().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
        userRef = mDatabaseReference.child("usersIDs");



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
    public void onClick(View view) {

        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid().toString();

        switch (view.getId()) {
            case R.id.noti_button_PP:
                startActivity(new Intent(ProfilePage.this, SchwiftPage.class));
                finish();
                break;
            case R.id.noti_copy_uid_PP:
                copyUID = uid;
                        myClip = ClipData.newPlainText("text", copyUID);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "UID Copied",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfilePage.this, HomePage.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
