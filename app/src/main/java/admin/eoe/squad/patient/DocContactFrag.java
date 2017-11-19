package admin.eoe.squad.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by admin on 13/07/2017.
 */

public class DocContactFrag extends Fragment {
    private TextView mail,phone,care,health;
    private DatabaseReference database;
    private String user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_contact,container,false);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mail = (TextView) view.findViewById(R.id.mail);
        phone = (TextView) view.findViewById(R.id.phone);
        care = (TextView) view.findViewById(R.id.care);
        health = (TextView) view.findViewById(R.id.health);

        return view;
    }
}
