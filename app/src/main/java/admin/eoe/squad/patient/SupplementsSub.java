package admin.eoe.squad.patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by admin on 22/07/2017.
 */

public class SupplementsSub extends AppCompatActivity{
    private EditText name,dos;
    private Spinner s1,s2,s3,s4;

    private DatabaseReference database;
    private ImageView i1,i2,i3,i4,back;
    private Button save;
    String a,b,c,d;
    private boolean boo = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplement_sub);
        i1 = (ImageView) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView ) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        i4 = (ImageView) findViewById(R.id.four);
        i4.setImageResource(R.mipmap.checkmark);
        final String[] time ={"12:00am",
                "01:00am", "02:00am","03:00am","04:00am","05:00am","06:00am","07:00am","08:00am","09:00am","10:00am","11:00am","12:00pm"
                ,"01:00pm","02:00pm","03:00pm","04:00pm","05:00pm","06:00pm","07:00pm","08:00pm","09:00pm","10:00pm","11:00pm"};
        final String[] frequency ={"once a day",
                "twice a day",
                "three times"};
        final String[] dosage={"mg","g"};

        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (EditText) findViewById(R.id.supp_et);
        dos = (EditText) findViewById(R.id.dosa_quantity);
        s1 =(Spinner) findViewById(R.id.supp_sub_1);

        s2 = (Spinner) findViewById(R.id.supp_sub_2);

        s3 = (Spinner) findViewById(R.id.supp_sub_3);
s4 = (Spinner) findViewById(R.id.cal);
        save = (Button) findViewById(R.id.supp_sub_button);
        final List<MedicationData> list = new ArrayList<>();
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a=dosage[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b=frequency[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c=time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d=time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MedicationData medic = new MedicationData(name.getText().toString(),dos.getText().toString()+a,c+", "+b,d);
                list.add(medic);
                database.child("suppsub").setValue(list);
            }
        });

    }


}
