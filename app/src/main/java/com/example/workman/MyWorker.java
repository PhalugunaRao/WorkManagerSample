package com.example.workman;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class MyWorker extends Worker {
    public  static final String KEY_TASK_OUTPUT="key_task_output";
    public MyWorker(@NonNull Context context, @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NotNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        String desc=data.getString(MainTestActivity.KEY_TASK_DES);
        displayNoti("Hey test",desc);

        Data data1=new Data.Builder()
                .putString(KEY_TASK_OUTPUT,"TASK FINISH Successfully")
                .build();

        return Result.success(data1);
    }

    private void displayNoti(String task,String desc){
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());
    }
}
