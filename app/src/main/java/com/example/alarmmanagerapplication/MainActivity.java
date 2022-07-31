package com.example.alarmmanagerapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int REQUEST_CODE = 1;
    private AlarmManager alarmManager;
    private PendingIntent clockPi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AlarmManager对象获取
        alarmManager = ((AlarmManager) getSystemService(Context.ALARM_SERVICE));

        findViewById(R.id.btn_set_alarm).setOnClickListener(this);
        findViewById(R.id.btn_set_serice).setOnClickListener(this);
        findViewById(R.id.btn_cancle_alarm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_set_alarm) {   //设置动作，启动的是Activity

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get
                    (Calendar.DAY_OF_MONTH), 0, 0, 0);
            if (clockPi == null) {
                //设置要启动的组件
                Intent intent = new Intent(this, ClockActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("alarm_time", "1234");
                intent.putExtras(bundle);
                clockPi = PendingIntent.getActivity(this, REQUEST_CODE, intent, 0);
            }

            Calendar calendar = Calendar.getInstance();

            /*
            //24小时制（设置指定时间）
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 15);
            calendar.set(Calendar.MINUTE, 58);
            calendar.set(Calendar.SECOND, 0);

            //12小时制（设置指定时间）
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 3);
            calendar.set(Calendar.MINUTE, 58);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.PM);
             */

            //方法一
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND) + 2);

            /*
            //方法二
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 10);
            */

            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), clockPi);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), clockPi);
            }
//            AlarmManagerCompat.setExact(alarmManager, AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), clockPi);

            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();

        } else if (id == R.id.btn_set_serice) {   //设置动作，启动的是Service

            Intent intent = new Intent(this, MyService.class);
            PendingIntent pi = PendingIntent.getService(this, REQUEST_CODE, intent, 0);
            long interval = DateUtils.MINUTE_IN_MILLIS * 2;
            long firstWake = System.currentTimeMillis();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstWake, interval, pi);

        } else if (id == R.id.btn_cancle_alarm) {

            //取消定时任务
            if (clockPi != null) {
                alarmManager.cancel(clockPi);
                Toast.makeText(this, "闹钟已取消", Toast.LENGTH_LONG).show();
            }
        }
    }

}