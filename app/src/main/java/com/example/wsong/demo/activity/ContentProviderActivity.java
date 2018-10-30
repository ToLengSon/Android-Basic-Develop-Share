package com.example.wsong.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.example.wsong.demo.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentProviderActivity extends BaseActivity {

    private static final int CONTACT_PERMISSIONS_REQUEST_CODE = 101;

    @BindView(R.id.contact_tv)
    TextView mContactTextView;

    public static void launch(Activity activity, String title) {
        Intent intent = new Intent(activity, ContentProviderActivity.class);
        intent.putExtra(ENTRY_VALUE_KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            int hasWriteContactsPermisson = checkSelfPermission(android.Manifest.permission.READ_CONTACTS);

            if(hasWriteContactsPermisson ==
                    PackageManager.PERMISSION_GRANTED) {
                displayContacts();
            } else {
                requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                        CONTACT_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            displayContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACT_PERMISSIONS_REQUEST_CODE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            displayContacts();
        }
    }

    private void displayContacts() {

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                null);

        if (cursor != null) {

            int i = 0;
            String[] data = new String[cursor.getCount()];

            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);

                data[i] = id + ", 姓名：" + name;

                Cursor phonesCusor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                if (phonesCusor != null) {
                    while (cursor.moveToNext()) {
                        String phoneNo = phonesCusor.getString(0);
                        data[i] += ", 电话号码：" + phoneNo;
                    }
                    phonesCusor.close();
                }
                i++;
            }
            cursor.close();

            mContactTextView.setText(Arrays.toString(data));
        }
    }
}
