package com.example.arcmember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class versionfail extends AppCompatActivity {
    int Cversion;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versionfail);
        Intent intent = getIntent();
        Cversion = intent.getIntExtra("version",0);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("version");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int version = dataSnapshot.getValue(Integer.class);
                    if(Cversion==version){
                        Intent intent = new Intent(versionfail.this, splash.class);
                        // Start the second activity
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(versionfail.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
            }
        });
    }
}