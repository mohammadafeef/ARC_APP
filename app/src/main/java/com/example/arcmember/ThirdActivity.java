package com.example.arcmember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.graphics.Color;

public class ThirdActivity extends AppCompatActivity {

// Other necessary imports
    String Username;
    String Status;

        private DatabaseReference switchesRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_third);

            DatabaseReference databaseReference;

            Intent intent = getIntent();
            Username = intent.getStringExtra("Username");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("access/"+Username+"/status");
            TextView feedback = findViewById(R.id.textView2);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String data = dataSnapshot.getValue(String.class);
                        Status=data;
                        if(data.equals("granted")){
                            feedback.setText("Granted");
                            feedback.setTextColor(Color.GREEN);
                        }
                        else{
                            feedback.setText("Denied");
                            feedback.setTextColor(Color.RED);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ThirdActivity.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
                }
            });

            switchesRef = FirebaseDatabase.getInstance().getReference().child("switches");

            Button button0 = findViewById(R.id.button15);
            Button button1 = findViewById(R.id.button16);
            Button button2 = findViewById(R.id.button17);
            Button button3 = findViewById(R.id.button18);
            // Define buttons 2 to 7 similarly

            setup(0, button0);
            setup(1, button1);
            setup(2, button2);
            setup(3, button3);

            DatabaseReference databaseReference2;

            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("switches");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    setup(0, button0);
                    setup(1, button1);
                    setup(2, button2);
                    setup(3, button3);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ThirdActivity.this, "Connectivity issue", Toast.LENGTH_SHORT).show();
                }
            });


            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButton(0, button0);
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButton(1, button1);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButton(2, button2);
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButton(3, button3);
                }
            });

            // Define click listeners for buttons 1 to 7 similarly
        }
        private void setup(int buttonNumber, Button button){
            switchesRef.child(String.valueOf(buttonNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int value = dataSnapshot.getValue(Integer.class);

                        // Update the button color based on the new value
                        if (value == 1) {
                            button.setBackgroundColor(Color.GREEN);
                        } else {
                            button.setBackgroundColor(Color.GRAY);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });
        }

        private void toggleButton(int buttonNumber, Button button) {
            if(Status.equals("granted")) {
                switchesRef.child(String.valueOf(buttonNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int value = dataSnapshot.getValue(Integer.class);
                            int newValue = (value == 0) ? 1 : 0;

                            // Update the value in Firebase
                            switchesRef.child(String.valueOf(buttonNumber)).setValue(newValue);

                            // Update the button color based on the new value
                            if (newValue == 1) {
                                button.setBackgroundColor(Color.GREEN);
                            } else {
                                button.setBackgroundColor(Color.GRAY);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors here
                    }
                });
            }
        }

}
