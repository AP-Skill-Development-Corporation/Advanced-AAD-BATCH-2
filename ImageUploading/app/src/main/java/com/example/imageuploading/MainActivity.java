package com.example.imageuploading;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name,et_mobile;
    ImageView iv;
    Button b_camera,b_gallery,b_save,b_read;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        iv = findViewById(R.id.selectedImage);
        b_camera = findViewById(R.id.cameraButton);
        b_gallery = findViewById(R.id.galleryButton);
        b_save = findViewById(R.id.saveButton);
        b_read = findViewById(R.id.readButton);

        b_camera.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_save.setOnClickListener(this);
        b_read.setOnClickListener(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cameraButton:
                openCamera();
                break;
            case R.id.galleryButton:
                openGallery();
                break;
            case R.id.saveButton:
                saveData();
                break;
            case R.id.readButton:
                readData();
                break;

        }

    }

    private void readData() {
    }

    private void saveData() {



    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,33);
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 22){
            if(resultCode == RESULT_OK){
                Bitmap b =(Bitmap) data.getExtras().get("data");
                Uri u = getImageUri(this,b);
                iv.setImageURI(u);
                imageUpload(u);
            }
        }else if(requestCode == 33){
            if(resultCode == RESULT_OK){
                Uri u = data.getData();
                iv.setImageURI(u);
                imageUpload(u);

            }

        }

    }

    private void imageUpload(Uri u) {
        storageReference = storageReference.child("Images/"+ UUID.randomUUID().toString());
        storageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(MainActivity.this, "Image Uploaded Successfully",
                        Toast.LENGTH_SHORT).show();
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String fileLocation = uri.toString();
                        if(fileLocation!=null){
                            String name = et_name.getText().toString();
                            String mobile = et_mobile.getText().toString();
                            User u = new User();
                            u.setFileLocation(fileLocation);
                            u.setName(name);
                            u.setMobile(mobile);
                            reference.child("User").push().setValue(u)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainActivity.this,
                                                    "Data Inserted", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }



                    }
                });




            }
        });


    }

    private Uri getImageUri(MainActivity mainActivity, Bitmap b) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media
                .insertImage(mainActivity.getContentResolver(),b,
                        "Title",null);
        return Uri.parse(path);
    }
}