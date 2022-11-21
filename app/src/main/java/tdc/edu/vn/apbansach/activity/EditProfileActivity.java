package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.User;

public class EditProfileActivity extends AppCompatActivity {
    ImageView ivUser;
    Button btnSave;
    EditText etUsername, etEmail;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String name, email;
    StorageReference storage;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setControl();
        setEvent();
    }

    private void setEvent() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference("ProfileDisplayImg");
        Uri uri = firebaseUser.getPhotoUrl();
        showProfile(firebaseUser);
        if (uri == null) {
            ivUser.setImageResource(R.drawable.user);
        } else {
            Picasso.get().load(uri).into(ivUser);
        }

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileActivity.this, UploadProfileImg_activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void updateProfile(FirebaseUser firebaseUser) {
        name = etUsername.getText().toString();
        email = etEmail.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etUsername.setError("Email is required!");
            etUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Password is required!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please provide valid email!");
            etEmail.requestFocus();
            return;
        }
        User userModel = new User(email, name);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = mAuth.getUid();
        databaseReference.child(userID).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    firebaseUser.updateProfile(changeRequest);
                    Toast.makeText(EditProfileActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userModel = snapshot.getValue(User.class);
                if (userModel != null) {
                    email = userModel.getEmail();
                    name = userModel.getFullname();
                    etUsername.setText(name);
                    etEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(i);
        finish();
    }

    private void setControl() {
        progressBar = findViewById(R.id.progress_bar);
        ivUser = findViewById(R.id.ivUserImg);
        btnSave = findViewById(R.id.btnSave);
        etEmail = findViewById(R.id.etMail);
        etUsername = findViewById(R.id.etUsername);
    }
}