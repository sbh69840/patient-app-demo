package admin.eoe.squad.patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.marcohc.robotocalendar.RobotoCalendarView;
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Sample Activity
 *
 * @author Marco Hernaiz Cao
 */
public class DocCalendar extends Fragment implements RobotoCalendarListener {
private RelativeLayout layout;
    private RobotoCalendarView robotoCalendarView;
private TextView viewmore;
    private DatabaseReference database;
    private String dat,date1;
    private Date date;
    private String suffix,url;
    private StorageReference mStorage;
    private ImageView iv,iv1;
    private Calendar calendar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view =inflater.inflate(R.layout.doc_calendar,container,false);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        robotoCalendarView = (RobotoCalendarView) view.findViewById(R.id.robotoCalendarPicker);
iv = (ImageView) view.findViewById(R.id.cal_img);
        iv1 = (ImageView) view.findViewById(R.id.cal_img_rep);
        layout = (RelativeLayout) view.findViewById(R.id.calendar_view);
viewmore=(TextView) view.findViewById(R.id.viewmore);

viewmore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(viewmore.getText().toString().equals("View More")) {
            layout.setVisibility(View.VISIBLE);
            viewmore.setText("View Less");
        }else{
            layout.setVisibility(View.GONE);
            iv1.setVisibility(View.GONE);
            viewmore.setText("View More");
        }
    }
});
        // Gets the calendar from the view
iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        iv1.setVisibility(View.VISIBLE);
    }
});


database.child("startdate").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()) {
            dat = dataSnapshot.getValue(String.class);
            try {
                date = new SimpleDateFormat("dd MM yyyy").parse(dat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar != null) {
                robotoCalendarView.setCalendar(calendar);
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.updateView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(CalligraphyContextWrapper.wrap(context));
    }



    @Override
    public void onDayClick(Calendar daySelectedCalendar) {
        Date now = daySelectedCalendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
        date1 = format.format(now);
        database.child("currentuser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suffix = dataSnapshot.getValue(String.class);
                mStorage = FirebaseStorage.getInstance().getReference().child("sym/"+suffix+date1);
                mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(!uri.toString().startsWith("http://")&& !uri.toString().startsWith("https://")){
                                    url = "http://"+uri.toString();
                                }else{
                                    url = uri.toString();
                                }
                                if(url!=null) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Sorry The user has not recorded any information for the day..",Toast.LENGTH_LONG).show();
                            }
                        });






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDayLongClick(Calendar daySelectedCalendar) {

    }

    @Override
    public void onRightButtonClick() {

    }

    @Override
    public void onLeftButtonClick() {

    }

}
