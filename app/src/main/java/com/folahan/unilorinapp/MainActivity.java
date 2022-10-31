package com.folahan.unilorinapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.CheckLevelActivity;
import com.folahan.unilorinapp.Activity.FriendsActivity;
import com.folahan.unilorinapp.Activity.LoginActivity;
import com.folahan.unilorinapp.Activity.QuestionTab;
import com.folahan.unilorinapp.Activity.Questions.QuestionPage;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.fragmentActivity.AccountFragment;
import com.folahan.unilorinapp.fragmentActivity.ActivateFragment;
import com.folahan.unilorinapp.fragmentActivity.HomeFragment;
import com.folahan.unilorinapp.fragmentActivity.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private TextView txtName, txtUsername, txtEmail;
    private View view;
    private PreferenceManager preferenceManager;
    private SharedPreferences mPreferences;
    private final String COUNT_KEY = "count";
    private int noOfClicks = 3;
    BottomNavigationView navigationView;
    AccountFragment fragment = new AccountFragment();
    ActivateFragment activateFragment = new ActivateFragment();
    HomeFragment homeFragment = new HomeFragment();
    private AlertDialog.Builder dialog;
    SettingsFragment settingsFragment = new SettingsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtUnilorinUpdate);


        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout, homeFragment).commit();
        navigationView = findViewById(R.id.bottom_nav);

        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frameLayout, homeFragment).commit();
                    return true;
                //
                case R.id.acct:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frameLayout, fragment).commit();
                    return true;
                //
                case R.id.activate:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frameLayout, activateFragment).commit();
                    return true;
                //
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frameLayout, settingsFragment).commit();
                    return true;
            }
            return false;
        });

        String sharedPrefFile = "com.folahan.android.mainactivity";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    }

    private void dialogAlert() {
        noOfClicks = mPreferences.getInt(COUNT_KEY, 3);
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activate Account")
                .setMessage("You have 3 default chances \n You are left with "+noOfClicks+" chances to go")
                .setPositiveButton("Proceed to Test", (dialogInterface, i) -> {
                    if (noOfClicks == 0) {
                        Toast.makeText(MainActivity.this, "Activate your account", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, QuestionTab.class);
                        startActivity(intent);
                        noOfClicks++;
                    }
                })
                .setNegativeButton("Activate Account", (dialog, which) -> getSupportFragmentManager().beginTransaction().
                        replace(R.id.frameLayout, activateFragment).commit())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_login)).show();
        /*dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.ThemeOverlay_AppCompat_ActionBar)
                .setTitle("")
                .setMessage("You have 3 default chances \n You are left with "+noOfClicks+" chances to go")
                .setPositiveButton("Proceed to Test", (dialog, which) -> {

                })
                .setNegativeButton("Activate Account", (dialog, which) -> getSupportFragmentManager().beginTransaction().
                        replace(R.id.frameLayout, activateFragment).commit())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_login)).show();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, noOfClicks);
        preferencesEditor.apply();
    }

    public void openQuestionTab(View view) {
        dialogAlert();
    }

    public void openLevelActivity(View view) {
        startActivity(new Intent(this, CheckLevelActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openQuestionPage(View view) {
        startActivity(new Intent(this, QuestionPage.class));
    }

    public void openFriendActivity(View view) {
        startActivity(new Intent(this, FriendsActivity.class));
    }

    public void openUnilorinUpdate(View view) {
        gotoUrl();
    }

    private void gotoUrl() {
        Uri uri = Uri.parse("https://www.uilugportal.unilorin.edu.ng");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}