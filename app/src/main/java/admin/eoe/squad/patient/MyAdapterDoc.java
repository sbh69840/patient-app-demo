package admin.eoe.squad.patient;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class MyAdapterDoc extends BaseAdapter {
    private final ArrayList mData;
    private DatabaseReference database;
    public MyAdapterDoc (Map<String,Object> map){
        mData = new ArrayList();
        mData.addAll(map.entrySet());
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
                database.child("currentuser").setValue((String)item.getValue());
                result.getContext().startActivity(new Intent(result.getContext(),DocMain.class));
            }
        });
        (result.findViewById(R.id.adapter_layout)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        ((TextView)result.findViewById(R.id.adapter_tv)).setText(item.getKey());
        return result;
    }

}
