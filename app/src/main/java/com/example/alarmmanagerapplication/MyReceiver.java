package com.example.alarmmanagerapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.AlarmManagerCompat;

import java.util.Calendar;
import java.util.Random;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.wearable.intent.action.REPORT_BEHAVIOR_ANALYTICS";
    public final int REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("MyReceiver", action);
        if (Intent.ACTION_DATE_CHANGED.equals(action)) {  //监听日期发送改变
            Random random = new Random();
            int delaySeconds = random.nextInt(5) + 1;
            Calendar calendar = getCalendar();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //设置动作，启动的是广播
            Intent i = new Intent(ACTION);
            PendingIntent pi = PendingIntent.getBroadcast(context, REQUEST_CODE, i, 0);
            alarmManager.cancel(pi);
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delaySeconds * 1000, pi);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delaySeconds * 1000, pi);
            }
//            AlarmManagerCompat.setExact(alarmManager, AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        } else if (ACTION.equals(action)) {
            Log.e("MyReceiver", "收到定时任务的广播");
            Toast.makeText(context, "收到定时任务的广播", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取凌晨0点的时间（24小时制）
     *
     * @return
     */
    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar;
    }
}