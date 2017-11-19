package admin.eoe.squad.patient;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 30/06/2017.
 */

public class MyAdapter extends BaseAdapter {
    private final ArrayList mData;
    private DatabaseReference database;
    public MyAdapter (Map<String,Object> map){
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }
public MyAdapter(List<String> map){
    mData = new ArrayList();
    mData.addAll(map);
}
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String,Object> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        final View result;
        if(convertView == null){
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_adapter,parent,false);
        }else{
            result = convertView;
        }
        final Map.Entry<String,Object> item = getItem(position);
        ( result.findViewById(R.id.adapter_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                final HashMap<String,Object> map1 = new HashMap<>();

                database.child("docmain").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            map1.putAll((HashMap<String,Object>)dataSnapshot.getValue());
                            map1.put(item.getKey(), item.getValue());
                            dataSnapshot.getRef().setValue(map1);
                        }else{
                            map1.put(item.getKey(), item.getValue());
                            database.child("docmain").setValue(map1);
                        }
                        Toast.makeText(result.getContext(),"You have added a patient to your list",Toast.LENGTH_LONG).show();
                        result.getContext().startActivity(new Intent(result.getContext(),DocMainPatients.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        ((TextView)result.findViewById(R.id.adapter_tv)).setText(item.getKey());
        return result;
    }

}
