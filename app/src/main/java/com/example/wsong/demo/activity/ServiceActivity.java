package com.example.wsong.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wsong.demo.R;

public class ServiceActivity extends BaseActivity {

    public static void launch(Activity activity, String title) {
        Intent intent = new Intent(activity, ServiceActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }
}
