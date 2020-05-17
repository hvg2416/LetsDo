package com.developerhvg.letsdoit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class PrivateTaskActivity extends AppCompatActivity {


    final static int NEW_PRIVATE_TASK_REQUEST_CODE = 2;
    static TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_task);

        final LottieAnimationView lottieAnimationView = findViewById(R.id.empty_task_list_anim_view);

        FloatingActionButton floatingActionButton = findViewById(R.id.FAB_new_private_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),NewTaskActivity.class);
                startActivityForResult(intent,NEW_PRIVATE_TASK_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_from_bottom_anim,R.anim.stay_activity);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.private_task_recycler_view);
        final CustomAdapter customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new TaskViewModel(getApplication());
        taskViewModel.getLiveProtectedData().observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntityList) {
                if(taskEntityList.size() == 0)
                {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else
                {
                    lottieAnimationView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Collections.reverse(taskEntityList);
                    customAdapter.setData(taskEntityList);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PRIVATE_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            String new_task_title = data.getStringExtra("new_task_title");
            String new_task_description = data.getStringExtra("new_task_description");
            String new_task_protection = "SECURED";
            TaskEntity taskEntity = new TaskEntity(new_task_title,new_task_description,new_task_protection);
            taskViewModel.insert(taskEntity);
            Toast.makeText(this,"SAVED",Toast.LENGTH_LONG).show();
        }
    }

    public static void delete(TaskEntity taskEntity)
    {
        taskViewModel.delete(taskEntity);
    }
}