package com.example.wsong.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wsong.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassValueActivity extends BaseActivity {

    /* 入口参数key */
    private static final String ENTRY_VALUE_KEY = "PASS_VALUE_ACTIVITY_ENTRY_VALUE_KEY";
    /* 返回值key */
    private static final String RETURN_VALUE_KEY = "PASS_VALUE_ACTIVITY_RETURN_VALUE_KEY";

    @BindView(R.id.pass_value_tv)
    TextView mPassValueTextView;
    @BindView(R.id.pass_value_et)
    EditText mPassValueEditText;

    /**
     * 启动PassValueActivity
     * @param activity 活动
     * @param value 传入的值
     */
    public static void launch(Activity activity, String title, String value, int requestCode) {
        Intent intent = new Intent(activity, PassValueActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY, value);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivityForResult(intent, requestCode);
    }

    public static String getReturnValue(Intent intent, String defaultValue) {
        if (intent != null) {
            return intent.getStringExtra(RETURN_VALUE_KEY);
        }
        return defaultValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_value);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String value = intent.getStringExtra(ENTRY_VALUE_KEY);
        mPassValueTextView.setText(value);
    }

    @OnClick(R.id.pass_value_btn)
    public void onPassValueBtnClick() {
        Intent intent = new Intent();
        intent.putExtra(RETURN_VALUE_KEY, mPassValueEditText.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
