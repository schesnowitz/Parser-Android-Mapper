package com.chesnowitz.parser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Application;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {


  public void redirectActivity() {

    if (ParseUser.getCurrentUser().getString("riderOrDriver").equals("rider")) {

      Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
      startActivity(intent);

    }
  }


  public void letsRoll(View view) {

    Switch userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

    Log.i("Switch value", String.valueOf(userTypeSwitch.isChecked()));

    String userType = "rider";

    if (userTypeSwitch.isChecked()) {

      userType = "driver";

    }

    ParseUser.getCurrentUser().put("riderOrDriver", userType);

    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        redirectActivity();

      }
    });




  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("vYJ3YcZoCUDjT2nbBugynzyR2Sk0TGYtXk4NKtLI")
            .clientKey("pL1AaeRMhRWLLVwFfzNMgxhzMAqOtJW9bBu1tzH5")
            .server("https://parseapi.back4app.com/").build()

    );
    ParseUser.enableRevocableSessionInBackground();


//    ParseObject gameScore = new ParseObject("GameScore");
//    gameScore.put("score", 1337);
//    gameScore.put("playerName", "Sean Plott");
//    gameScore.put("cheatMode", false);
//    gameScore.saveInBackground();


//    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
    getSupportActionBar().hide();

    setTitle("Truber");

    if (ParseUser.getCurrentUser() == null) {
      ParseAnonymousUtils.logIn(new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if (e == null) {
            Log.i("Info --> ", "Anonymous login was a success");
          } else {
            Log.i("Info --> ", "Anonymous login was a failed");
            e.printStackTrace();
          }
        }
      });
    } else {
      if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {
        Log.i("Info --> ", "Redirecting as" + ParseUser.getCurrentUser().get("riderOfDriver"));
      }
    }


    ParseAnalytics.trackAppOpenedInBackground(getIntent());

  }
}
