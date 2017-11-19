package admin.eoe.squad.patient;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by admin on 16/07/2017.
 */

public class NextAppointment extends AppCompatActivity  {
    private ImageView cal,time,loc,i1,i2,i3,i4,i5,back;
    private TextView loc_tv,loc1_tv,note;
    private Spinner cal_tv;
    private DatabaseReference database;
    private String a;
    private Button next;
    public static List<String> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_appointment);
        i1 = (ImageView) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView ) findViewById(R.id.two);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i2.setImageResource(R.mipmap.checkmark);
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        i4 = (ImageView) findViewById(R.id.four);
        i4.setImageResource(R.mipmap.checkmark);
        i5 = (ImageView) findViewById(R.id.five);
        i5.setImageResource(R.mipmap.checkmark);
        final String[] array ={ "1 month","2 months","3 months","4 month","5 month","6 month","7 month","8 month","9 month"};
        cal = (ImageView) findViewById(R.id.next_cal);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        time = (ImageView) findViewById(R.id.next_time);
       loc = (ImageView) findViewById(R.id.next_loc);
        loc_tv = (TextView) findViewById(R.id.next_time_tv);
        loc1_tv= (TextView) findViewById(R.id.next_loc_tv); 
        cal_tv = (Spinner) findViewById(R.id.next_val_tv);
        note = (TextView) findViewById(R.id.next_note_tv);
        next = (Button) findViewById(R.id.over_button);
        loc_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment fragment = new TimePickerFragment();
                fragment.show(getFragmentManager(),"Time");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment fragment = new TimePickerFragment();
                fragment.show(getFragmentManager(),"Time");
            }
        });
        cal_tv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a=array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        list = new ArrayList<>();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               list.add(a);
                list.add(loc_tv.getText().toString());
                list.add(loc1_tv.getText().toString());
                list.add(note.getText().toString());
                database.child("nextappoi").setValue(list);
                startActivity(new Intent(getApplicationContext(),OtherActivity.class));
            }
        });


    }


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current time as the default values for the time picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Create and return a new instance of TimePickerDialog
        /*
            public constructor.....
            TimePickerDialog(Context context, int theme,
             TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)

            The 'theme' parameter allow us to specify the theme of TimePickerDialog

            .......List of Themes.......
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL

         */
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                    ,this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

            //You can set a simple text title for TimePickerDialog
            //tpd.setTitle("Title Of Time Picker Dialog");

        /*.........Set a custom title for picker........*/
            TextView tvTitle = new TextView(getActivity());
            tvTitle.setText("TimePickerDialog Title");
            tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
            tvTitle.setPadding(5, 3, 5, 3);
            tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            tpd.setCustomTitle(tvTitle);
        /*.........End custom title section........*/

            return tpd;
        }

        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            //Do something with the user chosen time
            //Get reference of host activity (XML Layout File) TextView widget
            TextView tv = (TextView) getActivity().findViewById(R.id.next_time_tv);
            //Set a message for user

            //Get the AM or PM for current time
            String aMpM = "AM";
            if(hourOfDay >11)
            {
                aMpM = "PM";
            }

            //Make the 24 hour time format to 12 hour time format
            int currentHour;
            if(hourOfDay>11)
            {
                currentHour = hourOfDay - 12;
            }
            else
            {
                currentHour = hourOfDay;
            }


            //Display the user changed time on TextView
            tv.setText(tv.getText()+ String.valueOf(currentHour)
                    + " : " + String.valueOf(minute) + " " + aMpM + "\n");


        }
    }

}
