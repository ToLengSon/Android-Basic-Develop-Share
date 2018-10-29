package com.example.wsong.demo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsong.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BroadcastActivity extends BaseActivity {

    private static final String BROADCAST_DATA_MSG_KEY = "BROADCAST_ACTIVITY_BROADCAST_MSG_KEY";
    private static final String INTENT_FILTER_ACTION_KEY = "BROADCAST_ACTIVITY_INTENT_FILTER_ACTION_KEY";

    @BindView(R.id.message_et)
    EditText mMessageEditText;
    @BindView(R.id.receiver1_tv)
    TextView mReceiver1TV;
    @BindView(R.id.receiver2_tv)
    TextView mReceiver2TV;
    @BindView(R.id.receiver3_tv)
    TextView mReceiver3TV;

    private BroadcastDemoReceiver1 mReceiver1;
    private BroadcastDemoReceiver2 mReceiver2;
    private BroadcastDemoReceiver3 mReceiver3;

    public class BroadcastDemoReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mReceiver1TV.setText(intent.getStringExtra(BROADCAST_DATA_MSG_KEY));
            // 打开代码，只有1显示，2与3被拦截
            // abortBroadcast();
        }
    }

    public class BroadcastDemoReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mReceiver2TV.setText(intent.getStringExtra(BROADCAST_DATA_MSG_KEY));
            // 打开代码，只有1、2显示，3被拦截
            // abortBroadcast();
        }
    }

    public class BroadcastDemoReceiver3 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mReceiver3TV.setText(intent.getStringExtra(BROADCAST_DATA_MSG_KEY));
        }
    }

    public static void launch(Activity activity, String title) {
        Intent intent = new Intent(activity, BroadcastActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ButterKnife.bind(this);

        mReceiver1 = new BroadcastDemoReceiver1();
        IntentFilter intentFilter1 = new IntentFilter(INTENT_FILTER_ACTION_KEY);
        intentFilter1.setPriority(3);
        registerReceiver(mReceiver1, intentFilter1);

        mReceiver2 = new BroadcastDemoReceiver2();
        IntentFilter intentFilter2 = new IntentFilter(INTENT_FILTER_ACTION_KEY);
        intentFilter2.setPriority(2);
        registerReceiver(mReceiver2, intentFilter2);

        mReceiver3 = new BroadcastDemoReceiver3();
        IntentFilter intentFilter3 = new IntentFilter(INTENT_FILTER_ACTION_KEY);
        intentFilter3.setPriority(1);
        registerReceiver(mReceiver3, intentFilter3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver1);
        unregisterReceiver(mReceiver2);
        unregisterReceiver(mReceiver3);
    }

    @OnClick(R.id.send_btn)
    public void onSendBtnClick() {

        String message = mMessageEditText.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "请输入发送的消息", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(INTENT_FILTER_ACTION_KEY);

        intent.setPackage(getPackageName());
        intent.putExtra(BROADCAST_DATA_MSG_KEY, message);
        sendOrderedBroadcast(intent, null);

        // 该为异步广播，abortBroadcast()无效
        // sendBroadcast(intent);
    }
}
