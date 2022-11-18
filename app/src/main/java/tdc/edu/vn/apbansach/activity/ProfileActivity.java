package tdc.edu.vn.apbansach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivUser;
    private TextView tvUsername;
    private Button btnEdit, btnLogout, btnBack;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    public static final String SHARE_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setControl();
        setEvent();
    }

    private void setEvent() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "");
                editor.apply();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    private void setControl() {
        ivUser = findViewById(R.id.ivUserImg);
        tvUsername = findViewById(R.id.tvUserNameInfo);
        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
    }
}