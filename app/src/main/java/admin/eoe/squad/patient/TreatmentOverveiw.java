package admin.eoe.squad.patient;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by admin on 16/07/2017.
 */

public class TreatmentOverveiw extends AppCompatActivity {
    private CheckBox c1,c2,c3,c4;
    private Button next;
    private ImageView one,back;
    private DatabaseReference database;
    private String a,b,c,d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setContentView(R.layout.treatment_overview);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        c1 = (CheckBox) findViewById(R.id.over_checkBox);
        c2 = (CheckBox) findViewById(R.id.over_checkBox1);
        c3 = (CheckBox) findViewById(R.id.over_checkBox2);
        c4 = (CheckBox) findViewById(R.id.over_checkBox3);
        next = (Button) findViewById(R.id.over_button);
        one = (ImageView) findViewById(R.id.one);
        database.child("img").setValue("treat");
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1.isChecked()){
                    database.child("allergiescheckbox").setValue("1");
                }else{
                    database.child("allergiescheckbox").setValue("0");
                }
                if(c2.isChecked()){
                    database.child("dietcheckbox").setValue("1");
                }else{
                    database.child("dietcheckbox").setValue("0");
                }
                if(c3.isChecked()){
                    database.child("medicationcheckbox").setValue("1");
                }else{
                    database.child("medicationcheckbox").setValue("0");
                }
                if(c3.isChecked()){
                    database.child("supplementscheckbox").setValue("1");
                }else{
                    database.child("supplementscheckbox").setValue("0");
                }
                database.child("medicationcheckbox").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        c=dataSnapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child("supplementscheckbox").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        d=dataSnapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child("dietcheckbox").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        b=dataSnapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child("allergiescheckbox").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        a=dataSnapshot.getValue(String.class);
                        if(a.equals("1")){
                            startActivity(new Intent(getApplicationContext(),DietActivity.class));

                        }else if(b.equals("1")){
                            startActivity(new Intent(getApplicationContext(),EoeDiet.class));
                        }else if(c.equals("1")){
                            startActivity(new Intent(getApplicationContext(),MedicationActivity.class));
                        }else if(d.equals("1")){
                            startActivity(new Intent(getApplicationContext(),SupplementsActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
