package team.dev.bm.sktsmobile;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edttxtNama;
    private EditText edttxtUsername;
    private EditText edttxtPassword;
    private EditText edttxtEmail;

    private Button buttonRegister,buttonLogin;

    private static final String REGISTER_URL = "http://10.0.2.2/skts/Register.php";
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);

        animationDrawable =(AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);

        edttxtNama = (EditText) findViewById(R.id.edttxtNama);
        edttxtUsername = (EditText) findViewById(R.id.edttxtUserName);
        edttxtPassword = (EditText) findViewById(R.id.edttxtPassword);
        edttxtEmail = (EditText) findViewById(R.id.edttxtEmail);

        buttonRegister = (Button) findViewById(R.id.btnRegister);
        buttonLogin = (Button)findViewById(R.id.backlogin);
        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v== buttonLogin){
            startActivity(new Intent(RegisterActivity.this,Login.class));
            finish();
        }
    }


    private void registerUser() {
        String nama = edttxtNama.getText().toString().trim().toLowerCase();
        String username = edttxtUsername.getText().toString().trim().toLowerCase();
        String password = edttxtPassword.getText().toString().trim().toLowerCase();
        String email = edttxtEmail.getText().toString().trim().toLowerCase();

        register(nama, username, password, email);

    }

    private void register(String nama, String username, String password, String email) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("nama",params[0]);
                data.put("username",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(nama,username,password,email);


    }

}
