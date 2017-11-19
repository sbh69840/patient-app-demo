package admin.eoe.squad.patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 30/06/2017.
 */

public class EoeDietAdapter extends BaseAdapter implements DatePickerDialog.OnDateSetListener{
    private final ArrayList mData;
    private DatabaseReference database;
    private FrameLayout f1,f2;
    private TextView start;
    private EditText res;
    private Activity activity;
    private String[] array = {"No","Date"};
    private boolean w_date=true;
    private ArrayAdapter aa;
    private CheckBox cb;
    private Spinner re;
    public EoeDietAdapter(List<String> map,Activity activity){
        mData = new ArrayList();
        this.activity = activity;
        mData.addAll(map);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return (String) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        final View result;
        if(convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.eoe_diet_list_item,parent,false);
        }else{
            result = convertView;
        }
        final String item = getItem(position);
        f1 = (FrameLayout) result.findViewById(R.id.start_fl);
        f2 = (FrameLayout) result.findViewById(R.id.re);
        start = (TextView ) result.findViewById(R.id.start_tv);
        res = (EditText) result.findViewById(R.id.start_tv_re);
        re = (Spinner) result.findViewById(R.id.spinner);
        cb = (CheckBox) result.findViewById(R.id.li_cb);
       cb.setText(item);
        final List<EoedietData> list = new ArrayList<>();
        final HashSet<EoedietData> set = new HashSet<>();
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    f1.setVisibility(View.VISIBLE);
                    f2.setVisibility(View.VISIBLE);
                    aa = new ArrayAdapter(result.getContext(),android.R.layout.simple_spinner_item,array);
                    re.setAdapter(aa);
                    re.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==1){
                                datePicker(result.getContext());
                            }
                            EoedietData diet = new EoedietData(item,res.getText().toString(),array[position]);
                            list.add(diet);
                            database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            database.child("eoeadapter").setValue(list);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }else{
                    f1.setVisibility(View.GONE);
                    f2.setVisibility(View.GONE);
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(activity);

            }
        });





        return result;
    }
    public void datePicker( Context context){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(((Activity)context).getFragmentManager(),"date");
    }
    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
        res.setText(dateFormat.format(calendar.getTime()));
    }
    private String setDat(final Calendar calendar){
        final DateFormat dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM);
        return dateFormat.format(calendar.getTime());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year,month,dayOfMonth);
        if(w_date) {
            setDate(cal);
        }else{
            array[1]=setDat(cal);
            aa.notifyDataSetChanged();
        }
        w_date=false;
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

