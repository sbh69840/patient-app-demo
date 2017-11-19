package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by admin on 30/06/2017.
 */

public class Intake extends AppCompatActivity {
    private Button bf,lunch,dinner,snack1,snack2,snack3;
    private ImageView back,set;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intake);
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
        bf = (Button) findViewById(R.id.intake_breakfast);
        lunch = (Button) findViewById(R.id.intake_lunch);
        dinner = (Button) findViewById(R.id.intake_dinner);
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Breakfast.class));

            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Lunch.class));

            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dinner.class));

            }
        });
        snack1 = (Button) findViewById(R.id.intake_snack);
        snack2 = (Button) findViewById(R.id.intake_snack2);
        snack3 = (Button) findViewById(R.id.intake_snack3);
        snack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Snack.class));
            }
        });
        snack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Snack.class));
            }
        });
        snack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Snack.class));
            }
        });

    }
}
