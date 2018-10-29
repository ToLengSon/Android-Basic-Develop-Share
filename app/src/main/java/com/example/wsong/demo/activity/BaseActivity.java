package com.example.wsong.demo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity_Debug";

    protected static final String ENTRY_VALUE_KEY_TITLE = "BASE_ACTIVITY_TITLE_ENTRY_VALUE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarTitle(getIntent().getStringExtra(ENTRY_VALUE_KEY_TITLE));

        Log.d(TAG, "onCreate: " + getClass().getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + getClass().getSimpleName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + getClass().getSimpleName());
    }

    protected void setActionBarTitle(String actionBarTitle) {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(actionBarTitle == null? getLocalClassName() : actionBarTitle);
        }
    }
}
