package admin.eoe.squad.patient;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by admin on 19/07/2017.
 */

public class EoeDiet extends AppCompatActivity {
    private CheckBox c1,c2;
    private Button b1;
    private ImageView i1,i2,back;
    private DatabaseReference database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eoe_diet);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        i1 = (ImageView) findViewById(R.id.one);
        back = (ImageView) findViewById(R.id.wtback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        i1.setImageResource(R.mipmap.checkmark);
        i2 = (ImageView) findViewById(R.id.two);
        i2.setImageResource(R.mipmap.checkmark);
        c1 = (CheckBox) findViewById(R.id.diet_checkBox_asf);
        c2 = (CheckBox) findViewById(R.id.diet_checkBox1_efo);
        b1 = (Button) findViewById(R.id.diet_button_add);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        List<EoedietData> list1=new ArrayList<>();
        list1.add(new EoedietData("peanut","July 2, 2017","No"));
        database.child("eoeadapter").setValue(list1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1.isChecked()){
                    database.child("eoediet").setValue(c1.getText().toString());

                }else{
                    database.child("eoediet").setValue(c2.getText().toString());
                }
                //start an activity
                startActivity(new Intent(getApplicationContext(),Eoedietlist.class));
            }
        });
    }
}
