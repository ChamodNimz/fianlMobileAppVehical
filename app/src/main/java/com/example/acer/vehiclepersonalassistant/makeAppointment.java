package com.example.acer.vehiclepersonalassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class makeAppointment extends AppCompatActivity {

    TextView prevDate, newDate;
    CalendarView calendarView;
    EditText time;
    Button Confirm, Cancel;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        FirebaseApp.initializeApp(makeAppointment.this);


        prevDate = findViewById(R.id.txtLastDate);
        calendarView = findViewById(R.id.calendarView);
        time = findViewById(R.id.txtTime);
        Confirm = findViewById(R.id.btnConfirm);
        Cancel = findViewById(R.id.btnCancel);
        db = FirebaseFirestore.getInstance();
        newDate = findViewById(R.id.txtDate);


        db.collection("Appointments").document("details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists() && documentSnapshot!= null)
                    {
                        String date =documentSnapshot.getString("lastAppointmentDate");
                        prevDate.setText(date);
                    }
                    else
                    {
                        Toast.makeText(makeAppointment.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String date1 = (month + 1)+ "/" +dayOfMonth +"/" +year;
                    newDate.setText(date1);
            }
        });


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datevalue = String.valueOf(newDate.getText());
                String Time = String.valueOf(time.getText());
                Map<String, String> usermap = new HashMap<>();
                usermap.put("newDate", datevalue);
                usermap.put("time", Time);
                db.collection("Appointments").add(usermap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(makeAppointment.this,"Details added",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Toast.makeText(makeAppointment.this,"Details were not added",Toast.LENGTH_SHORT).show();
                    }
                });

               // final Intent goToPrevActivity = new Intent(makeAppointment.this,MainActivity.class);

            }
        });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });



    }

    public void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
