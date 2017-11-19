package admin.eoe.squad.patient;

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
 * Created by admin on 22/07/2017.
 */

public class OtherSub extends AppCompatActivity {
    private EditText et;
    private ImageView i1,i2,i3,i4,i5,i6,back;
    private Button save;
    private DatabaseReference database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_sub);
        i1 = (ImageView) findViewById(R.id.one);
        i1.setImageResource(R.mipmap.checkmark);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i2 = (ImageView ) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        i3 = (ImageView ) findViewById(R.id.three);
        i3.setImageResource(R.mipmap.checkmark);
        i4 = (ImageView) findViewById(R.id.four);
        i4.setImageResource(R.mipmap.checkmark);
        i5 = (ImageView) findViewById(R.id.five);
        i5.setImageResource(R.mipmap.checkmark);
        i6 = (ImageView) findViewById(R.id.six);
        i6.setImageResource(R.mipmap.checkmark);
        et = (EditText) findViewById(R.id.other_et);
        save = (Button) findViewById(R.id.other_save_button);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("othersub").setValue(et.getText().toString());
                finish();
            }
        });
    }
}
