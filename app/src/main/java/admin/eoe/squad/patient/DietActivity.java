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
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/06/2017.
 */

public class DietActivity extends AppCompatActivity {
    private CheckBox c1;
    private CheckBox c2;
    private CheckBox c3;
    private CheckBox c4;
    private CheckBox c5;
    private EditText others;
    private DatabaseReference database;
    private List<String> diet;
    private Button next;
    private String b,c,d;
    private ImageView one,back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet);
        diet = new ArrayList<>();
        one = (ImageView) findViewById(R.id.one);
        one.setImageResource(R.mipmap.checkmark);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        c1 = (CheckBox) findViewById(R.id.diet_checkBox);
        c2 = (CheckBox) findViewById(R.id.diet_checkBox1);
        c3 = (CheckBox) findViewById(R.id.diet_checkBox2);
        c4 = (CheckBox) findViewById(R.id.diet_checkBox3);
        c5 = (CheckBox) findViewById(R.id.diet_checkBox4);
        others = (EditText) findViewById(R.id.other_diet);
        next = (Button) findViewById(R.id.diet_button);
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
        c5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to open medications Activity
                if(c1.isChecked()){
                    diet.add(c1.getText().toString());
                }
                if(c2.isChecked()){
                    diet.add(c2.getText().toString());
                }
                if(c3.isChecked()){
                    diet.add(c3.getText().toString());
                }
                if(c4.isChecked()){
                    diet.add(c4.getText().toString());
                }
                if(c5.isChecked()){
                    diet.add(c5.getText().toString());
                }
                String[] other = others.getText().toString().trim().split(",");
                for(String a : other){
                    diet.add(a);
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
                        Intent i =new Intent();
                        if(b.equals("1")){
                            i =new Intent(getApplicationContext(),EoeDiet.class);
                        }else if(c.equals("1")){
                            i = new Intent(getApplicationContext(),MedicationActivity.class);
                        }else if(d.equals("1")){
                            i = new Intent(getApplicationContext(),SupplementsActivity.class);
                        }
                        database.child("diet").setValue(diet);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });
    }
}
