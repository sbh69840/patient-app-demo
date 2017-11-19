package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.PopupWindowCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by admin on 27/06/2017.
 */

public class TreatmentFragment extends Fragment {
    private DatabaseReference database;
    private TextView score,text;
    private Button next;
    private PopupWindowCompat popup;
    public TreatmentFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treatment,container,false);
        final List<String> name = new ArrayList<>();
        next = (Button) view.findViewById(R.id.button_treatment);
        text = (TextView) view.findViewById(R.id.toolbar_name);
        score = (TextView) view.findViewById(R.id.score);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        database.child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                int res = Integer.valueOf(s)*100;
                score.setText(String.valueOf(res));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    name.add((String) data.getValue());
                }
                text.setText(name.get(0));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what to do when I click on the next
                startActivity(new Intent(getActivity(),TreatmentOverveiw.class));
            }
        });
        return view;
    }
}
