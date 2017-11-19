package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by admin on 13/07/2017.
 */

public class DocMain extends AppCompatActivity {
    private TabLayout tab;
    private DocViewAdapter vpa;
    private TextView name,dob;
    private ImageView iv,set;
    private ViewPager vp;
    private DatabaseReference database,database1;
    private String name1,dob1,user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_patient);
        iv= (ImageView) findViewById(R.id.back_img);
        set = (ImageView) findViewById(R.id.menudot);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (TextView) findViewById(R.id.name_doc_patient);
        dob = (TextView) findViewById(R.id.dob_doc_patient);
        vpa = new DocViewAdapter(getSupportFragmentManager());
        vp = (ViewPager) findViewById(R.id.doc_viewpager);
        vp.setAdapter(vpa);
        tab = (TabLayout) findViewById(R.id.doc_sliding_tabs);
        tab.setupWithViewPager(vp);
        vp.setCurrentItem(0);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DocMainPatients.class));
            }
        });
database.child("currentuser").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        user = dataSnapshot.getValue(String.class);
        database1 = FirebaseDatabase.getInstance().getReference(user);
        database1.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    name1+=data.getValue(String.class)+" ";
                }
                if(name1!=null) {
                    name1 = name1.substring(4);
                    name.setText(name1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database1.child("birthday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) {
                    dob1 += "DOB: ";
                    dob1 += data.getValue(String.class);
                    if (dob1.length() > 9) {
                        dob1 = dob1.substring(4);
                        dob.setText(dob1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



    }
}
