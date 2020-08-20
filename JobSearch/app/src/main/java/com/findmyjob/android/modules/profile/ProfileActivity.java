package com.findmyjob.android.modules.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ImageView img1,img2;
    TextView text;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        TabLayout tabLayout = findViewById(R.id.tablayot_id);
        ViewPager viewPager = findViewById(R.id.view_pager_profile);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        text=findViewById(R.id.text1);
        fAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        fStore.collection("users").document(Objects.requireNonNull(this.fAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name=fAuth.getCurrentUser().getDisplayName();

                text.setText(documentSnapshot.getString("name"));
            }


        });


        StorageReference profileRef = storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img1);
            }
        });



        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallary,1000);
            }

        });


        ViewPageAdaptor adaptor = new ViewPageAdaptor(getSupportFragmentManager());
        adaptor.AddFragment(new PersonalFragment(),"Personal Details");
        adaptor.AddFragment(new EducationFragment(),"Education Details");

        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            if (resultCode== Activity.RESULT_OK){
                assert data != null;
                Uri imgUri= data.getData();
                img1.setImageURI(imgUri);
                UploadImg(imgUri);
            }
        }

    }

    private void UploadImg(Uri imgUri) {
        //upload img
        final StorageReference fileRef= storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid()+"profile.jpg");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img1);
                    }
                });

                Toast.makeText(ProfileActivity.this,"img Uploaded..",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this,"img Uploading failed..",Toast.LENGTH_SHORT).show();

            }
        });


    }
    }


