package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import edu.montclair.mobilecomputing.r_soltes.schwifty.LoginPage;

/**
 * Created by ryansoltes on 4/19/17.
 */

public class sessionUser {

//    public static final String SHARED_PREF_FILENAME="edu.montclair.mobilecomputing.r_soltes.schwifty.SHAREDFILE1";
//    public static final String KEY_SESSION_USER_ID="";
//    public static final String KEY_SESSION_USERNAME="";
//    public static final String KEY_SESSION_USEREMAIL="";
//    public static final String KEY_SESSION_USERROLE="";

//    public String name;
//    public String email;
//    public String userRole;
//    public String uId;




    public sessionUser(){

    }


    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String SHARED_PREF_FILENAME="edu.montclair.mobilecomputing.r_soltes.schwifty.USERSESSIONFILE";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_SESSION_USER_ID="";
    public static final String KEY_SESSION_USERNAME="";
    public static final String KEY_SESSION_USEREMAIL="";
    public static final String KEY_SESSION_USERROLE="";

    public static String getKeySessionUsername() {
        return KEY_SESSION_USERNAME;
    }

    // Constructor
    public sessionUser(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(SHARED_PREF_FILENAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createSessionUser(String email,String uId,String name, String userRole){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing values in pref
        editor.putString(KEY_SESSION_USEREMAIL, email);
        editor.putString(KEY_SESSION_USER_ID, uId);
        editor.putString(KEY_SESSION_USERNAME, name);
        editor.putString(KEY_SESSION_USERROLE, userRole);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // Get values in pref
        user.put(KEY_SESSION_USEREMAIL, pref.getString(KEY_SESSION_USEREMAIL, null));
        user.put(KEY_SESSION_USER_ID, pref.getString(KEY_SESSION_USER_ID, null));
        user.put(KEY_SESSION_USERNAME, pref.getString(KEY_SESSION_USERNAME, null));
        user.put(KEY_SESSION_USERROLE, pref.getString(KEY_SESSION_USERROLE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginPage.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }




















    /**
 *
 *
 *
 *
 * */


//    public sessionUser() {
//        // Default constructor required for calls to DataSnapshot.getValue(User.class)
//    }
//
//    public sessionUser(String email,String uId,String name, String userRole) {
//        this.name = name;
//        this.email = email;
//        this.userRole = userRole;
//        this.uId = uId;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(String userRole) {
//        this.userRole = userRole;
//    }
//
//    public String getuId() {
//        return uId;
//    }
//
//    public void setuId(String uId) {
//        this.uId = uId;
//    }
}
