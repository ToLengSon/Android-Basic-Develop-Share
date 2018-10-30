package com.example.wsong.demo.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.wsong.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    private interface MusicController {
        void play();
        void pause();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicController = (MusicController)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private MusicController mMusicController;

    public static class ServiceService extends Service {

        MediaPlayer mMediaPlayer;

        class MyBinder extends Binder implements MusicController {

            @Override
            public void play() {
                mMediaPlayer.start();
            }

            @Override
            public void pause() {
                mMediaPlayer.pause();
            }
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return new MyBinder();
        }

        @Override
        public void onCreate() {
            super.onCreate();
            mMediaPlayer = MediaPlayer.create(this, R.raw.father);
        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
            }
        }
    }

    public static void launch(Activity activity, String title) {
        Intent intent = new Intent(activity, ServiceActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);

        bindService(new Intent(this, ServiceService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @OnClick(R.id.control_btn)
    public void onControlBtnClick(Button controlBtn) {

        String title = controlBtn.getText().toString();

        if ("播放".equals(title)) {
            // 改为暂停，并且开始播放音乐
            controlBtn.setText("暂停");
            if (mMusicController != null) {
                mMusicController.play();
            }
        } else {
            // 改为播放，并且结束播放音乐
            controlBtn.setText("播放");
            if (mMusicController != null) {
                mMusicController.pause();
            }
        }
    }
}
