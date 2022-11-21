package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import tdc.edu.vn.apbansach.R;

public class UploadProfileImg_activity extends AppCompatActivity {
    ImageView imgUpload;
    Button btnChoose, btnUpload, btnBack;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    StorageReference storage;
    ProgressBar progressBar;
    private static final  int PICK_IMAGE_REQUEST= 1;
    private Uri uriImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_img);
        setControl();
        setEvent();
    }

    private void setEvent() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference("ProfileDisplayImg");
        Uri uri = firebaseUser.getPhotoUrl();
        Picasso.get().load(uri).into(imgUpload);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opemFile();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                uploadPic();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UploadProfileImg_activity.this, EditProfileActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private void uploadPic() {
        if (uriImg != null){
            StorageReference reference = storage.child(mAuth.getCurrentUser().getUid() + "." + getFileExtention(uriImg));
            reference.putFile(uriImg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri download = uri;
                            firebaseUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(download).build();
                            firebaseUser.updateProfile(profileChangeRequest);
                            Toast.makeText(UploadProfileImg_activity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private String getFileExtention(Uri uriImg) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uriImg));
    }

    private void opemFile() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==PICK_IMAGE_REQUEST && resultCode== RESULT_OK && data.getData() != null && data != null){
            uriImg = data.getData();
            imgUpload.setImageURI(uriImg);
        }
    }

    private void setControl() {
        imgUpload = findViewById(R.id.imgUpload);
        btnChoose = findViewById(R.id.btnChooseImg);
        btnUpload = findViewById(R.id.btnUpload);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progress_bar);
    }
}