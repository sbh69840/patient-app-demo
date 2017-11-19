package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 28/06/2017.
 */

public class HowLong extends AppCompatActivity {

    private EditText text;
    private Button next;
    private DatabaseReference database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_long);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        text = (EditText) findViewById(R.id.tv);
        next =(Button) findViewById(R.id.button_long);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(text.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Enter the weeks",Toast.LENGTH_SHORT).show();
                }else{
                    Date now = new Date();
                    android.text.format.DateFormat.format("yyyy-MM-dd", now);
                    SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
                    String date = format.format(now);
                    List<String> week = new ArrayList<>();
                    week.add(text.getText().toString());
                    database.child("startdate").setValue(date);
                    database.child("week").setValue(week);
                    startActivity(new Intent(getApplicationContext(),DocMainPatients.class));
finish();
                }
            }
        });
    }
}
