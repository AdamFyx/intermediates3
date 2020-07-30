package com.example.intermediates;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    TextView nameText;
    TextView pwdText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginBtn=findViewById(R.id.loginbtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = findViewById(R.id.username);
                pwdText = findViewById(R.id.password);
                if (nameText.getText().toString().equals("")) {
                    nameText.setHint("请输入账号");
                    return;
                }
                if (pwdText.getText().toString().equals("")) {
                    pwdText.setHint("请输入密码");
                    return;
                }
                if(nameText.getText().toString().equals("admin")&&pwdText.getText().toString().equals("admin")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }



}