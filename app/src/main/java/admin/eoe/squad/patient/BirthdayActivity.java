package admin.eoe.squad.patient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BirthdayActivity extends Activity implements DatePickerDialog.OnDateSetListener{
private ImageView one,two,three;
    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate,next;
    private DatabaseReference database;
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthdat);
        one= (ImageView) findViewById(R.id.one);
        one.setImageResource(R.mipmap.checkmark);
        two = (ImageView) findViewById(R.id.two);
        two.setImageResource(R.mipmap.checkmark);
        three = (ImageView) findViewById(R.id.three);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        next = (Button) findViewById(R.id.button_birthday);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to launch after clicking on next
                List<String> birth = new ArrayList<>();
                birth.add(tvDisplayDate.getText().toString());
                three.setImageResource(R.mipmap.checkmark);
                database.child("birthday").setValue(birth);
                startActivity(new Intent(getApplicationContext(),WantToTrack.class));
            }
        });
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
    }

    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker


    }

    public void addListenerOnButton() {


        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            StringBuilder date =new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" ");
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also


        }
    };
    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(),"date");
    }
    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
        tvDisplayDate.setText(dateFormat.format(calendar.getTime()));
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year,month,dayOfMonth);
        setDate(cal);
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