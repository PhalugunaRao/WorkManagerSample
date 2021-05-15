package com.example.workman;

import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainTestActivity extends AppCompatActivity {
    public  static final String KEY_TASK_DES="key_task_desc";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder()
                .putString(KEY_TASK_DES,"Hey sending sample data").build();

        Constraints constraints= new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest request= new OneTimeWorkRequest.Builder(MyWorker.class).
                setInputData(data).
                setConstraints(constraints).
                build();

        OneTimeWorkRequest request2= new OneTimeWorkRequest.Builder(MyWorker.class).
                setInputData(data).
                setConstraints(constraints).
                build();
        OneTimeWorkRequest request3= new OneTimeWorkRequest.Builder(MyWorker.class).
                setInputData(data).
                setConstraints(constraints).
                build();

        PeriodicWorkRequest myrequest = new PeriodicWorkRequest.Builder(
                MyWorker.class,20, TimeUnit.MINUTES
        ).build();

        findViewById(R.id.myview).setOnClickListener(view -> {
            WorkManager.getInstance(this).enqueue(request);

        });

        /*
        multiple request
        WorkManager.getInstance(this).beginWith(request)
                .then(request2).enqueue();


*/
        /*WorkManager.getInstance(this).beginWith(request,request2).then(request3).enqueue();
        WorkManager.getInstance(this).beginWith(request,request2)
                .then(request3).enqueue();*/
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            if(workInfo.getState().isFinished()){
                                Data outputData=workInfo.getOutputData();
                                System.out.println("MainTestActivity.onChanged===2" + outputData.getString(MyWorker.KEY_TASK_OUTPUT));

                            }
                            String status = workInfo.getState().name();
                            System.out.println("MainTestActivity.onChanged===" + status);
                        }
                    }
                });
    }
}
