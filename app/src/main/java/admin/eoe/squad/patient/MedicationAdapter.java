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

public class MedicationAdapter extends BaseAdapter implements DatePickerDialog.OnDateSetListener{
    private final ArrayList mData;
    private DatabaseReference database;
    private FrameLayout f1,f2;
    private TextView start,res;
    private Activity activity;
    private String[] array = {"No","Date"};
    private boolean w_date=true;
    private ArrayAdapter aa;
    private CheckBox cb;
    private Spinner re;
    public MedicationAdapter(List<MedicationData> map){
        mData = new ArrayList();
        this.activity = activity;
        mData.addAll(map);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MedicationData getItem(int position) {
        return (MedicationData) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        final View result;
        if(convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list_item,parent,false);
        }else{
            result = convertView;
        }
        final MedicationData item = getItem(position);
        TextView name= (TextView) result.findViewById(R.id.medic_name);
        name.setText(item.getName());
        TextView quantity= (TextView) result.findViewById(R.id.medic_quantinty);
        quantity.setText(item.getQuantity());
        TextView time= (TextView) result.findViewById(R.id.medic_time);
        time.setText(item.getTime());
        TextView date= (TextView) result.findViewById(R.id.medic_date);
        date.setText(item.getDate());





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
