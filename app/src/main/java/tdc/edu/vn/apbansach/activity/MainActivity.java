package tdc.edu.vn.apbansach.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.databinding.ActivityMainBinding;
import tdc.edu.vn.apbansach.fragment.CartFragment;
import tdc.edu.vn.apbansach.fragment.HomeFragment;
import tdc.edu.vn.apbansach.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
Button btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       setControl();
       setEvent();
    }

    private void setEvent() {
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void setControl() {
        btnProfile = findViewById(R.id.btnProfile);
    }
}