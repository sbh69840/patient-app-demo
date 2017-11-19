package admin.eoe.squad.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 27/06/2017.
 */

public class BookFragment extends Fragment {
    private TextView name,score;
    private ImageView set;
    private DatabaseReference database;
    public BookFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book,container,false);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (TextView) view.findViewById(R.id.toolbar_name);
set = (ImageView ) view.findViewById(R.id.menu_dash);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        score = (TextView) view.findViewById(R.id.score);
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
                List<String> name1 = new ArrayList<String>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    name1.add((String) data.getValue());
                }
                name.setText(name1.get(0));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            Uri uri =data.getData();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"image/*");
            startActivity(intent);
        }
    }
}
