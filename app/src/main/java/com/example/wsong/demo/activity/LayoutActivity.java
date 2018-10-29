package com.example.wsong.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wsong.demo.R;

public class LayoutActivity extends BaseActivity {

    /* 入口参数key */
    private static final String ENTRY_VALUE_KEY = "LAYOUT_ACTIVITY_ENTRY_VALUE_KEY";

    public static void launch(Activity activity, String title, int activityResId) {
        Intent intent = new Intent(activity, LayoutActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY, activityResId);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int activityResId = intent.getIntExtra(ENTRY_VALUE_KEY, R.layout.activity_layout);

        setContentView(activityResId);


    }
}
