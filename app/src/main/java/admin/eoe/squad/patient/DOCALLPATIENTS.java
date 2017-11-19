package admin.eoe.squad.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.HashMap;
import java.util.List;


/**
 * Created by admin on 09/07/2017.
 */

public class DOCALLPATIENTS extends AppCompatActivity{
    private ListView lv;
    private DatabaseReference database;
    private ImageView back,search;
    private EditText search_et;
    private TextView eoe;
    private MyAdapter adapter;
    private HashMap<String,Object> map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_all_patients);
        map = new HashMap<>();
        database = FirebaseDatabase.getInstance().getReference();
        eoe = (TextView) findViewById(R.id.textView);
        search = (ImageView) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.doc_list);
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
                    MyAdapter adapterDoc = new MyAdapter(list);
                    lv.setAdapter(adapterDoc);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name="";
                String uid="";
                for(DataSnapshot data:dataSnapshot.getChildren()){

                        if(data.hasChild("name")){
                            for(String a : (List<String>)data.child("name").getValue()){
                                name+=a+" ";
                            }

                        }
                        if(data.hasChild("uid")){
                            uid+=data.child("uid").getValue(String.class);
                        }
                        map.put(name,uid);
                        name="";
                        uid="";

                }

                adapter = new MyAdapter(map);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        finish();
    }
});
    }
}
