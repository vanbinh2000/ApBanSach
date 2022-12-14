package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import tdc.edu.vn.apbansach.R;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp;
    Button btnLogin;
    EditText txtMail, txtPass;
    FirebaseAuth mAuth;
    ImageView ivShowHideLoginPass;
    public static final String SHARE_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        checkRememberMe();
        ivShowHideLoginPass.setImageResource(R.drawable.hide);
        ivShowHideLoginPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    txtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShowHideLoginPass.setImageResource(R.drawable.hide);
                    txtPass.setSelection(txtPass.getText().length());
                }
                else{
                    txtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShowHideLoginPass.setImageResource(R.drawable.view);
                    txtPass.setSelection(txtPass.getText().length());
                }
            }
        });
    }

    private void checkRememberMe() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");
        if (check.equals("true")){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void login() {
        String Email = txtMail.getText().toString().trim();
        String Pass = txtPass.getText().toString().trim();
        if (TextUtils.isEmpty(Email)) {
            txtMail.setError("Email is required!");
            txtMail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pass)) {
            txtPass.setError("Password is required!");
            txtPass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            txtMail.setError("Please provide valid email!");
            txtMail.requestFocus();
            return;
        }
        if (Pass.length() < 6) {
            txtPass.setError("Min password length shound be 6");
            txtPass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "true");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {
        tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        txtMail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        ivShowHideLoginPass = findViewById(R.id.ivShowHideLoginPass);
    }
}