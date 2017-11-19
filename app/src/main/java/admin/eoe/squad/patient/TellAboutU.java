package admin.eoe.squad.patient;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/06/2017.
 */

public class TellAboutU extends AppCompatActivity {
    private CheckBox c1;
    private CheckBox c2;
    private CheckBox c3;
private ImageView one,back;
    private EditText others;
    private DatabaseReference database;
    private List<String> diet;
    private Button next;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tell_us_about_you);
        diet = new ArrayList<>();
        one = (ImageView) findViewById(R.id.one);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        c1 = (CheckBox) findViewById(R.id.checkBox2);
        c2 = (CheckBox) findViewById(R.id.checkBox3);
        c3 = (CheckBox) findViewById(R.id.checkBox4);
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
        c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setTextColor(Color.BLACK);
                }
            }
        });
        next = (Button) findViewById(R.id.button);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to open medications Activity
                if(c1.isChecked()){
                    diet.add(c1.getText().toString());
                }
                if(c2.isChecked()){
                    diet.add(c2.getText().toString());
                }
                if(c3.isChecked()){
                    diet.add(c3.getText().toString());
                }

one.setImageResource(R.mipmap.checkmark);
                final Intent i = new Intent(getApplicationContext(),Name_activity.class);
                database.child("aboutu").setValue(diet);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.email_conf, (ViewGroup)v.getParent());

                popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
                ((ViewGroup)layout.getParent()).removeAllViews();

                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
                Button button = (Button) layout.findViewById(R.id.button_wow);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(i);
                        finish();
                    }
                });
                ImageView image = (ImageView) layout.findViewById(R.id.email_cancel);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(i);
                        finish();
                    }
                });
                ((ViewGroup)v.getParent()).removeView(v);

            }
        });
    }
}

