package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/06/2017.
 */

public class Name_activity extends AppCompatActivity {
    DatabaseReference database ;
    private EditText first_name,last_name;
    private Button next;
private ImageView one,two;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
one= (ImageView) findViewById(R.id.one);
        one.setImageResource(R.mipmap.checkmark);
        two = (ImageView) findViewById(R.id.two);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);

        next = (Button) findViewById(R.id.button_name);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two.setImageResource(R.mipmap.checkmark);
                List<String> name = new ArrayList<>();
                name.add(first_name.getText().toString());
                name.add(last_name.getText().toString());
                database.child("name").setValue(name);
                database.child("score").setValue("0");
                Intent i = new Intent(getApplicationContext(),BirthdayActivity.class);
                startActivity(i);
            }
        });
    }
}
