package bangalore.bms.studentinformationportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginPage extends AppCompatActivity{

    Button login;
    EditText UsernameEt, PasswordEt;
    Context ctx;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        UsernameEt = (EditText)findViewById(R.id.un);
        PasswordEt = (EditText)findViewById(R.id.pwd);
        login =(Button)findViewById(R.id.login1);
        ctx=this;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UsernameEt.getText().toString();
                String password = PasswordEt.getText().toString();
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
                backgroundWorker.execute(type, username, password);

            }
        });

        }

    }

