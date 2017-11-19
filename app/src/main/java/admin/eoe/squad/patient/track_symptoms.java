package admin.eoe.squad.patient;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivraj on 26/06/2017.
 */

public class track_symptoms extends AppCompatActivity {
    private CheckBox c1;
    private CheckBox c2;
    private CheckBox c3;
    private CheckBox c4;
    private CheckBox c5;
    private EditText others;
    private DatabaseReference database;
    private List<String> medication;
    private Button next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_symptoms);
        medication = new ArrayList<>();
        c1 = (CheckBox) findViewById(R.id.t_checkBox);
        c2 = (CheckBox) findViewById(R.id.t_checkBox1);
        c3 = (CheckBox) findViewById(R.id.t_checkBox2);
        c4 = (CheckBox) findViewById(R.id.t_checkBox3);
        c5 = (CheckBox) findViewById(R.id.t_checkBox4);
        others = (EditText) findViewById(R.id.other_t);
        next = (Button) findViewById(R.id.t_button);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1.isChecked()){
                    medication.add(c1.getText().toString());
                }
                if(c2.isChecked()){
                    medication.add(c2.getText().toString());
                }
                if(c3.isChecked()){
                    medication.add(c3.getText().toString());
                }
                if(c4.isChecked()){
                    medication.add(c4.getText().toString());
                }
                if(c5.isChecked()){
                    medication.add(c5.getText().toString());
                }
                String[] other = others.getText().toString().trim().split(",");
                for(String a : other){
                    medication.add(a);
                }
                //intent to open medications Activity
                database.child("tracksymptoms").setValue(medication);
startActivity(new Intent(getApplicationContext(),Intake.class));
                finish();
            }
        });
    }
}
