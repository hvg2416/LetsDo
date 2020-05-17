package com.developerhvg.letsdoit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PrivateTaskCredentialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_task_credential);

        final RelativeLayout new_password_relative_layout,password_relative_layout;
        new_password_relative_layout = findViewById(R.id.new_password_relative_layout);
        password_relative_layout = findViewById(R.id.password_relative_layout);

        final SharedPreferences sharedPreferences = getSharedPreferences("auth_shared_prefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!(sharedPreferences.getString("password","null").equals("null")))
        {
            new_password_relative_layout.setVisibility(View.GONE);
        }
        else
        {
            password_relative_layout.setVisibility(View.GONE);
        }

        final EditText new_pass_edit_text,new_confirm_pass_edit_text,pass_edit_text;
        new_pass_edit_text = findViewById(R.id.new_password_edit_text);
        new_confirm_pass_edit_text = findViewById(R.id.new_confirm_password_edit_text);
        pass_edit_text = findViewById(R.id.password_edit_text);

        Button btn_check_pass,btn_set_new_password;
        btn_check_pass = findViewById(R.id.btn_check_password);
        btn_set_new_password = findViewById(R.id.btn_set_new_password);

        final Intent intent = new Intent(this,PrivateTaskActivity.class);

        btn_set_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_pass_edit_text.getText().toString().equals(new_confirm_pass_edit_text.getText().toString()))
                {
                    editor.putString("password",new_pass_edit_text.getText().toString());
                    editor.commit();
                    Toast.makeText(getBaseContext(),"Password set successfully",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_check_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass_edit_text.getText().toString().equals(sharedPreferences.getString("password","null")))
                {
                    Toast.makeText(getBaseContext(),"Password Matched",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView forgot_pass_text_view = findViewById(R.id.forget_password_text_view);
        forgot_pass_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_relative_layout.setVisibility(View.GONE);
                new_password_relative_layout.setVisibility(View.VISIBLE);
            }
        });
    }
}
