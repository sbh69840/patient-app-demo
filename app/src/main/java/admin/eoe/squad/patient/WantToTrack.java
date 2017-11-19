package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
 * Created by admin on 20/07/2017.
 */

public class WantToTrack extends AppCompatActivity {
    private CoordinatorLayout layout;
    private ImageView one,two,three,four;
    private DatabaseReference database;
    private String who;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_to_track);
        one= (ImageView) findViewById(R.id.one);
        one.setImageResource(R.mipmap.checkmark);
        two = (ImageView) findViewById(R.id.two);
        two.setImageResource(R.mipmap.checkmark);
        four = (ImageView) findViewById(R.id.four);
        three = (ImageView) findViewById(R.id.three);
        three.setImageResource(R.mipmap.checkmark);
        layout = (CoordinatorLayout) findViewById(R.id.colay);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                database.child("aboutu").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            who = data.getValue(String.class);
                            if(who.equals("I am a Patient")){
                                startActivity(new Intent(WantToTrack.this, HomeMainFragment.class));
                                finish();
                            }else{
                                startActivity(new Intent(WantToTrack.this,DocMainPatients.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
