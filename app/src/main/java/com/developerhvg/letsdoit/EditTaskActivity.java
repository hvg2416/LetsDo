package com.developerhvg.letsdoit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        final String task_title,task_description,task_protection;
        final int task_id;

        task_id = intent.getIntExtra("task_id",0);
        task_title = intent.getStringExtra("task_title");
        task_description = intent.getStringExtra("task_desc");
        task_protection = intent.getStringExtra("task_protection");

        final EditText task_title_edit_text,task_description_edit_text;
        task_title_edit_text = findViewById(R.id.edit_task_title_edit_text);
        task_description_edit_text = findViewById(R.id.edit_task_description_edit_text);

        task_title_edit_text.setText(task_title);
        task_description_edit_text.setText(task_description);

        final TaskViewModel taskViewModel = new TaskViewModel(getApplication());

        FloatingActionButton floatingActionButton = findViewById(R.id.FAB_update_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editted_task_title,editted_task_description;
                editted_task_title = task_title_edit_text.getText().toString();
                editted_task_description = task_description_edit_text.getText().toString();
                String editted_task_protection = task_protection;
                TaskEntity taskEntity = new TaskEntity(editted_task_title,editted_task_description,task_protection);
                taskEntity.task_id = task_id;
                taskViewModel.update(taskEntity);
                Toast.makeText(getBaseContext(),"Updated",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
