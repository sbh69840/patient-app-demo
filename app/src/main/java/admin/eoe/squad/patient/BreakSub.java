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

/**
 * Created by admin on 23/07/2017.
 */

public class BreakSub extends AppCompatActivity {
    private EditText text;
    private Button add;
    private DatabaseReference database;
    private ImageView back,set;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breakfast_sub);
        back = (ImageView) findViewById(R.id.toolbar_back_intake);
        set = (ImageView) findViewById(R.id.menu_intake);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        text = (EditText) findViewById(R.id.break_sub);
        add = (Button) findViewById(R.id.break_sub_button);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("breaksub").setValue(text.getText().toString());
            }
        });
    }
}
