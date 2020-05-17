package com.developerhvg.letsdoit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        FloatingActionButton floatingActionButton = findViewById(R.id.FAB_save_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText new_task_title_edit_text  = findViewById(R.id.new_task_title_edit_text);
                EditText new_task_description_edit_text = findViewById(R.id.new_task_description_edit_text);

                String new_task_title,new_task_description;
                new_task_title = new_task_title_edit_text.getText().toString();
                new_task_description = new_task_description_edit_text.getText().toString();

                if(new_task_title == null || new_task_title.equals(""))
                {
                    new_task_title_edit_text.requestFocus();
                    new_task_title_edit_text.setError("Title can not be empty");
                }
                else
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("new_task_title",new_task_title);
                    returnIntent.putExtra("new_task_description",new_task_description);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.stay_activity,R.anim.slide_to_bottom_anim);
    }
}
