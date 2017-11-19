package admin.eoe.squad.patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 19/07/2017.
 */

public class Eoedietlist extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
private FrameLayout f1,f2;
    private ListView lv;
    private Button next;
    private ImageView i1,i2;
    private TextView start,res;
    private String c,d;
    private List<EoedietData> ship;
private DatabaseReference database;
    private List<String> list;
    private Spinner re;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eoe_diet_main);
        i1 = (ImageView) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        ship = new ArrayList<>();
        next = (Button) findViewById(R.id.diet_list_but);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("supplementscheckbox").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        d=dataSnapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child("medicationcheckbox").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        c=dataSnapshot.getValue(String.class);
                        Intent i =new Intent();
                        if(c.equals("1")){
                            i = new Intent(getApplicationContext(),MedicationActivity.class);
                        }else if(d.equals("1")){
                            i = new Intent(getApplicationContext(),SupplementsActivity.class);
                        }

                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });
database= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        lv = (ListView) findViewById(R.id.eoe_diet_main_lv);
        list= new ArrayList<>();
        database.child("diet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    list.add(data.getValue(String.class));

                }
                EoeDietAdapter adapter = new EoeDietAdapter(list,Eoedietlist.this);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
database.child("eoeadapter").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String name,reint,start;
        List<String> list = new ArrayList<String>();
        for(DataSnapshot data:dataSnapshot.getChildren()){
            for(DataSnapshot data1:data.getChildren()){
                list.add(data1.getValue(String.class));
            }
        }
        name = list.get(0);
        reint = list.get(1);
        start = list.get(2);
        EoedietData dataa = new EoedietData(name,start,reint);
        ship.add(dataa);
        database.child("eoesubmain").setValue(ship);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
