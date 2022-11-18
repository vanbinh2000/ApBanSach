package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.User;

public class RegisterActivity extends AppCompatActivity {
    TextView tvLogin;
    EditText txtSignUpEmail, txtSignUpName, txtSignUpPass, txtSignUpConfirm;
    ImageView ivShowHideSignUpPass, ivShowHideSignUpConfirmPass;
    Button btnSignUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        ivShowHideSignUpPass.setImageResource(R.drawable.hide);
        ivShowHideSignUpPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtSignUpPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    show(txtSignUpPass, ivShowHideSignUpPass);
                    txtSignUpPass.setSelection(txtSignUpPass.getText().length());
                }
                else{
                    hide(txtSignUpPass, ivShowHideSignUpPass);
                    txtSignUpPass.setSelection(txtSignUpPass.getText().length());
                }
            }
        });
        ivShowHideSignUpConfirmPass.setImageResource(R.drawable.hide);
        ivShowHideSignUpConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtSignUpConfirm.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    show(txtSignUpConfirm, ivShowHideSignUpConfirmPass);
                    txtSignUpConfirm.setSelection(txtSignUpConfirm.getText().length());
                }
                else{
                    hide(txtSignUpConfirm, ivShowHideSignUpConfirmPass);
                    txtSignUpConfirm.setSelection(txtSignUpConfirm.getText().length());
                }
            }
        });
    }

    private void show(TextView textView, ImageView imageView) {
        textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imageView.setImageResource(R.drawable.hide);
    }
    private void hide(TextView textView, ImageView imageView) {
        textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        imageView.setImageResource(R.drawable.view);
    }
    private void register() {
        String Email, Username, Pass, Passcomfirm;
        Email = txtSignUpEmail.getText().toString().trim();
        Username = txtSignUpName.getText().toString().trim();
        Pass = txtSignUpPass.getText().toString().trim();
        Passcomfirm = txtSignUpConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(Email)) {
            txtSignUpEmail.setError("Email is required!");
            txtSignUpEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Username)) {
            txtSignUpName.setError("Name is required!");
            txtSignUpName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pass)) {
            txtSignUpPass.setError("Password is required!");
            txtSignUpPass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Passcomfirm)) {
            txtSignUpConfirm.setError("Password needs to be confirmed");
            txtSignUpConfirm.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            txtSignUpEmail.setError("Please provide valid email!");
            txtSignUpEmail.requestFocus();
            return;
        }
        if (Pass.length() < 6) {
            txtSignUpPass.setError("Min password length shound be 6");
            txtSignUpPass.requestFocus();
            return;
        }
        if (!Pass.equals(Passcomfirm)) {
            txtSignUpConfirm.setError("Need to be the same as the password");
            txtSignUpConfirm.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(Email, Username, Pass);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this, "SignUp unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void setControl() {
        tvLogin = findViewById(R.id.tvLogin);
        txtSignUpEmail = findViewById(R.id.txtSignUpEmail);
        txtSignUpName = findViewById(R.id.txtSignUpName);
        txtSignUpPass = findViewById(R.id.txtSignUpPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignUpConfirm = findViewById(R.id.txtSignUpConfirmPass);
        ivShowHideSignUpConfirmPass = findViewById(R.id.ivShowHideConfirmPass);
        ivShowHideSignUpPass = findViewById(R.id.ivShowHideSignUpPass);
    }
}