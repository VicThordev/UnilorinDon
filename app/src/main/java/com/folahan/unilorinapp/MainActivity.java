package com.folahan.unilorinapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.folahan.unilorinapp.Activity.CheckLevelActivity;
import com.folahan.unilorinapp.Activity.FriendsActivity;
import com.folahan.unilorinapp.Activity.LoginActivity;
import com.folahan.unilorinapp.Activity.QuestionTab;
import com.folahan.unilorinapp.Activity.Questions.CommentActivity;
import com.folahan.unilorinapp.Activity.Questions.QuestionPage;
import com.folahan.unilorinapp.Activity.RecentChatActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.fragmentActivity.AccountFragment;
import com.folahan.unilorinapp.fragmentActivity.ActivateFragment;
import com.folahan.unilorinapp.fragmentActivity.HomeFragment;
import com.folahan.unilorinapp.fragmentActivity.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private TextView txtName, txtUsername, txtEmail;
    private View view;
    private PreferenceManager preferenceManager;
    BottomNavigationView navigationView;
    AccountFragment fragment = new AccountFragment();
    ActivateFragment activateFragment = new ActivateFragment();
    HomeFragment homeFragment = new HomeFragment();
    SettingsFragment settingsFragment = new SettingsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

    }



    public void openQuestionTab(View view) {
        Intent intent = new Intent(this, RecentChatActivity.class);
        startActivity(intent);
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
}