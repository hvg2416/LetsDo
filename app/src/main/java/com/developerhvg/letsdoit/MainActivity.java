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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int NEW_TASK_REQUEST_CODE = 1;
    static TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LottieAnimationView lottieAnimationView = findViewById(R.id.empty_task_list_anim_view);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),NewTaskActivity.class);
                startActivityForResult(intent,NEW_TASK_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_from_bottom_anim,R.anim.stay_activity);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final CustomAdapter customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new TaskViewModel(getApplication());
        taskViewModel.getLiveData().observe(this, new Observer<List<TaskEntity>>() {
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

        ImageView lock_image_view = findViewById(R.id.lock_image_view);
        lock_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),PrivateTaskCredentialActivity.class);
                startActivity(intent);
            }
        });

        ImageView info_image_view = findViewById(R.id.info_image_view);
        info_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View popup_view = inflater.inflate(R.layout.popup_window_layout,null,false);
                final PopupWindow popupWindow = new PopupWindow(popup_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,false);
                Button btn_dismiss = popup_view.findViewById(R.id.btn_dismiss);
                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(findViewById(R.id.main),Gravity.CENTER,0,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            String new_task_title = data.getStringExtra("new_task_title");
            String new_task_description = data.getStringExtra("new_task_description");
            String new_task_protection = "UNSECURED";
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
