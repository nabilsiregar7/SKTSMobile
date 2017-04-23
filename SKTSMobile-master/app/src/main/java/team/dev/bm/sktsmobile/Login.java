package team.dev.bm.sktsmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener{
    public static final String USER_ID = "ID";
    public static final String USER_USERNAME = "username";

    public static final String USER_PASSWORD = "password";

    private static final String LOGIN_URL = "http://10.0.2.2/skts/login.php";

    private EditText edttxtUserName;
    private EditText edttxtPassword;
    private AnimationDrawable animationDrawable;
    private Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);

        animationDrawable =(AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);

        edttxtUserName = (EditText) findViewById(R.id.loginemail);
        edttxtPassword = (EditText) findViewById(R.id.loginPassword);

        btnLogin = (Button) findViewById(R.id.loginLogin);
        btnRegister = (Button) findViewById(R.id.loginRegister);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void login(){
        String username = edttxtUserName.getText().toString().trim();
        String password = edttxtPassword.getText().toString().trim();
        userLogin(username, password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("Sukses")){
                    Intent intent = new Intent(Login.this,WelcomeActivity.class);
                    intent.putExtra(USER_USERNAME,username);
                    intent.putExtra(USER_PASSWORD,password);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("username",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username,password);
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            login();

        }

        if(v == btnRegister){
            startActivity(new Intent(Login.this,RegisterActivity.class));
            finish();
        }
    }
}
