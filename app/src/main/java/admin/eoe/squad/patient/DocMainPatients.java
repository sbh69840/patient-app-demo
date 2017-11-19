package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 10/07/2017.
 */

public class DocMainPatients extends AppCompatActivity {
    private ListView lv;
    private FloatingActionButton fab;
    private HashMap<String,Object> map;
    private DatabaseReference database;
    private ImageView search,back;
    private EditText search_et;
    private TextView eoe,pat;
    private MyAdapterDoc adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_main_patients);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        lv = (ListView) findViewById(R.id.doc_main_list);
        fab = (FloatingActionButton) findViewById(R.id.doc_main_fab);
        map = new HashMap<>();
        eoe = (TextView) findViewById(R.id.textView);
        search = (ImageView) findViewById(R.id.search);
        back = (ImageView) findViewById(R.id.imageView2);
        search_et = (EditText) findViewById(R.id.doc_tool_et);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_et.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                eoe.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_et.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                eoe.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                lv.setAdapter(adapter);
            }
        });

        database.child("docmain").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    map.put(data.getKey(),data.getValue());
                     adapter = new MyAdapterDoc(map);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

search_et.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        String searchString = search_et.getText().toString();
        int textLength = searchString.length();

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
if(count>0){
    String ab = s.toString();
    HashMap<String,Object> list = new HashMap<>();
    for(int i=0 ; i<adapter.getCount();i++){
        if(adapter.getItem(i).getKey().startsWith(ab)){
            list.put(adapter.getItem(i).getKey(),adapter.getItem(i).getValue());
        }
    }
    MyAdapterDoc adapterDoc = new MyAdapterDoc(list);
    lv.setAdapter(adapterDoc);
}

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DOCALLPATIENTS.class));
            }
        });
    }
}
