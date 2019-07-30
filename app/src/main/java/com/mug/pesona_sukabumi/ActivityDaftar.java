package com.mug.pesona_sukabumi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.mug.pesona_sukabumi.R;

import java.util.HashMap;
import java.util.Map;

public class ActivityDaftar extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;
    private ProgressBar pBar;

    private EditText UserEmail,Username, Password,confPassword;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        ActionBar menu = getSupportActionBar();

        menu.setDisplayShowHomeEnabled(true);

        menu.setDisplayHomeAsUpEnabled(true);

        menu.setTitle("Daftar");

        //init views
        Username = findViewById(R.id.etUsername);
        UserEmail = findViewById(R.id.email);
        confPassword = findViewById(R.id.etCOnfirm);
        Password = findViewById(R.id.etPassword);
        regBtn = findViewById(R.id.btnDaftar);
        pBar = (ProgressBar) findViewById(R.id.pBar);

        mAuth = FirebaseAuth.getInstance();
        pBar.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(View.VISIBLE);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = Username.getText().toString();
                final String email = UserEmail.getText().toString();
                final String password = Password.getText().toString();
                final String confpass = confPassword.getText().toString();




                if(email .isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals(confpass))
                {
                    //error message
                    showMessage("Mohon Cek Kembali Data Anda");
                    pBar.setVisibility(View.INVISIBLE);
                    regBtn.setVisibility(View.VISIBLE);
                }
                else {
                    //create user account
                    pBar.setVisibility(View.VISIBLE);
                    regBtn.setVisibility(View.INVISIBLE);
                    CreateUSerAccount (name,email,password);

                }
            }
        });

        ImgUserPhoto = findViewById(R.id.img_user);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22){
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }

            }


        });

    }

    private void CreateUSerAccount(final String name,  final String email,final String password) {

        //create account with specific email, password
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //user account created succesfull

                            //update user profile
                            updateUserInfo(name, pickedImgUri, mAuth.getCurrentUser());


                        }
                        else {
                            //created account failed
                            showMessage("Account creation failed " + task.getException().getMessage());
                            pBar.setVisibility(View.INVISIBLE);
                            regBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    //update user info
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
        pBar = (ProgressBar) findViewById(R.id.pBar);

        firestore = FirebaseFirestore.getInstance();
        final Map<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("image", pickedImgUri.toString());
        final String userId = mAuth.getCurrentUser().getUid();
        //upload user photo to firebase storage and get url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photo").child(userId + ".jpg");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image upload success
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){



                                            //user info update

                                            firestore.collection("Users").document(userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        pBar.setVisibility(View.INVISIBLE);
                                                        updateUI();
                                                        showMessage("Account Created");

                                                    }else
                                                    {
                                                        String error = task.getException().getMessage();
                                                    }


                                                }
                                            });


                                        }
                                    }
                                });
                    }
                });
            }
        });



    }

    private void updateUI() {
        Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    //toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){

            //user has successfully picked an image
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);
        }

    }

    private void openGallery() {
        //TODO : open gallery intent and wait for user to pick an image
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(ActivityDaftar.this, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(ActivityDaftar.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(ActivityDaftar.this, "Please accept for required permission",Toast.LENGTH_SHORT).show();

            }
            else
            {
                ActivityCompat.requestPermissions(ActivityDaftar.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
        {
            openGallery();
        }

    }

    public void masuk(View view) {
        Intent intent = new Intent(ActivityDaftar.this, ActivityLogin.class);
        startActivity(intent);
    }
}
