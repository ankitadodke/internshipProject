package com.findmyjob.android.modules.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PersonalFragment extends Fragment {
    View view;
    String name,phone,email;
    TextInputEditText mName, mProf,mLocation,Mphone,memail, adrress;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    public PersonalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.personal_fragment,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mName = view.findViewById(R.id.eTxtFirstName);
        mProf=view.findViewById(R.id.profession);
        mLocation=view.findViewById(R.id.location);
        Mphone=view.findViewById(R.id.phone);
        memail=view.findViewById(R.id.email);
        adrress= view.findViewById(R.id.address);


        fStore.collection("users").document(this.firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PersonalFragment fragmentPersonal= PersonalFragment.this;
                phone= firebaseAuth.getCurrentUser().getPhoneNumber();
                name=firebaseAuth.getCurrentUser().getDisplayName();
                email = firebaseAuth.getCurrentUser().getEmail();

                mName.setText(documentSnapshot.getString("name"));
                memail.setText(documentSnapshot.getString("email"));
                mLocation.setText(documentSnapshot.getString("location"));
                mProf.setText(documentSnapshot.getString("profession"));
                adrress.setText(documentSnapshot.getString("address"));
                PersonalFragment.this.Mphone.setText(phone);

              }


        });

        return view;



    }


}
