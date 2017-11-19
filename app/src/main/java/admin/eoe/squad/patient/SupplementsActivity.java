package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

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

public class SupplementsActivity extends AppCompatActivity {
    private ImageView i1,i2,i3,back;
    private List<String> medication;
    private ListView lv;
    public static List<MedicationData> list;
    private Button add,done;
    private DatabaseReference database;
    private String d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplements_mamin);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i1 = (ImageView ) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView ) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        list = new ArrayList<>();
        medication = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        add = (Button) findViewById(R.id.supp_button);
        done = (Button) findViewById(R.id.supp_add_button);
        lv = (ListView) findViewById(R.id.eoe_supp_main_lv);
        database.child("suppsub").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String name,quantity,time,date;
                    List<String> array = new ArrayList<String>();
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        for(DataSnapshot data1:data.getChildren()) {
                            array.add(data1.getValue(String.class));
                        }
                    }
                    name = array.get(1);
                    quantity = array.get(2);
                    time = array.get(3);
                    date = array.get(0);
                    MedicationData medic = new MedicationData(name,quantity,time,date);
                    list.add(medic);
                    MedicationAdapter adapter = new MedicationAdapter(list);
                    lv.setAdapter(adapter);
                    database.child("suppsubmain").setValue(list);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),NextAppointment.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SupplementsSub.class));

            }
        });



    }
}

