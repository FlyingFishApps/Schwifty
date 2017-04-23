package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;
import edu.montclair.mobilecomputing.r_soltes.schwifty.utils.sessionUser;


public class HomeFragment extends Fragment {

    public Snackbar snackbar;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, userRef;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Fragment fragment = null;
    public sessionUser session;


    public HomeFragment(){
        // Empty Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * TODO Display jobs based on current user
         * **/

//
//        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
//        final String uid = user.getUid().toString();
//        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://schwifty-33650.firebaseio.com/");
//        userRef = mDatabaseReference.child("users");
//        userRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String userRole = dataSnapshot.child(uid).child("userRole").getValue().toString();
//                String username = dataSnapshot.child(uid).child("username").getValue().toString();
//                String userEmail = dataSnapshot.child(uid).child("email").getValue().toString();
//
//                session.createSessionUser(userEmail,uid,username,userRole);
//                HashMap<String, String> currentUser = session.getUserDetails();
//
//                System.out.println(currentUser);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

}
