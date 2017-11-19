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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 29/06/2017.
 */

public class SupplementsLength extends AppCompatActivity {
    private EditText fre,time;
    private Button next;
    private DatabaseReference database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplements_length);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        fre = (EditText) findViewById(R.id.supplements_frequent);
        time = (EditText) findViewById(R.id.day_time_supplements);
        next = (Button) findViewById(R.id.supplements_time_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(fre.getText())||TextUtils.isEmpty(time.getText())){
                    Toast.makeText(getApplicationContext(),"Enter both the boxes",Toast.LENGTH_SHORT).show();
                }else{
                    List<String> medic_length = new ArrayList<>();
                    medic_length.add(fre.getText().toString());
                    medic_length.add(time.getText().toString());
                    database.child("supplength").setValue(medic_length);
                    startActivity(new Intent(getApplicationContext(),TreatmentMain.class));
                    finish();
                }
            }
        });

    }

}

