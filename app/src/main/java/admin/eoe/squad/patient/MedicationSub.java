package admin.eoe.squad.patient;

import android.app.Activity;
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

public class MedicationSub extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private EditText name,dos;
    private Spinner s1,s2,s3;
    private TextView from,to,from_a,to_a;
    private DatabaseReference database;
    private ImageView i1,i2,i3,back;
    private Button save;
    String a,b,c;
    private boolean boo = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_sub);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i1 = (ImageView) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView ) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        final String[] time ={"12:00am",
        "01:00am", "02:00am","03:00am","04:00am","05:00am","06:00am","07:00am","08:00am","09:00am","10:00am","11:00am","12:00pm"
        ,"01:00pm","02:00pm","03:00pm","04:00pm","05:00pm","06:00pm","07:00pm","08:00pm","09:00pm","10:00pm","11:00pm"};
        final String[] frequency ={"once a day",
        "twice a day",
        "three times"};
        final String[] dosage={"mg","g"};
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,time);
        ArrayAdapter ab = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,frequency);
        ArrayAdapter ac = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,dosage);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (EditText) findViewById(R.id.sub_name);
        dos = (EditText) findViewById(R.id.dos_quantity);
        s1 =(Spinner) findViewById(R.id.dosage);

        s2 = (Spinner) findViewById(R.id.frequency);

        s3 = (Spinner) findViewById(R.id.time);

        from = (TextView) findViewById(R.id.from);
        from_a = (TextView ) findViewById(R.id.from_a);
        to_a=(TextView ) findViewById(R.id.to_a);
        to = (TextView) findViewById(R.id.to);
        save = (Button) findViewById(R.id.medic_sub_button); 
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MedicationData medic = new MedicationData(name.getText().toString(),dos.getText().toString()+a,c+", "+b,from.getText().toString()+"-"+to.getText().toString());
                list.add(medic);
                database.child("medicsub").setValue(list);
            }
        });
        from_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(getApplicationContext());
            }
        });
        to_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(getApplicationContext());
            }
        });
     }

    public void datePicker( Context context){
        EoeDietAdapter.DatePickerFragment fragment = new EoeDietAdapter.DatePickerFragment();
        fragment.show(this.getFragmentManager(),"date");
    }
    private void setDate(final Calendar calendar,TextView view){
        final DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
        view.setText(dateFormat.format(calendar.getTime()));
    }
    private String setDat(final Calendar calendar){
        final DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
        return dateFormat.format(calendar.getTime());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year,month,dayOfMonth);
        if(boo) {
            setDate(cal,from);
        }else{
            setDate(cal,to);
        }
        boo=false;
    }
    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        }
    }
}
