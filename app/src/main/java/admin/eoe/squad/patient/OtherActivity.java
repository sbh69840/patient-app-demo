package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 * Created by admin on 22/07/2017.
 */

public class OtherActivity extends AppCompatActivity {
    private ImageView i1,i2,i3,i4,i5,i6,back;
    private List<String> medication;
    private ListView lv;
    public static List<String> list;
    private Button add,done;
    private DatabaseReference database;
    private String d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_main);
        i1 = (ImageView ) findViewById(R.id.one);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView ) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        i4 = (ImageView) findViewById(R.id.four);
        i4.setImageResource(R.mipmap.checkmark);
        i5 = (ImageView) findViewById(R.id.five);
        i5.setImageResource(R.mipmap.checkmark);
        i6 = (ImageView) findViewById(R.id.six);
        i6.setImageResource(R.mipmap.checkmark);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        list = new ArrayList<>();
        medication = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        add = (Button) findViewById(R.id.other_button);
        done = (Button) findViewById(R.id.other_add_button);
        lv = (ListView) findViewById(R.id.eoe_other_main_lv);
        database.child("othersub").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {


                    list.add(dataSnapshot.getValue(String.class));
                    ArrayAdapter adapter = new ArrayAdapter(OtherActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,list);
                    lv.setAdapter(adapter);
                    database.child("othersubmain").setValue(list);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ScoreMain.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OtherSub.class));

            }
        });



    }
}
