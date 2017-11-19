package admin.eoe.squad.patient;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by admin on 27/06/2017.
 */

public class TrackingFragment extends Fragment {
    private ImageView symptoms,intake,pollen,today,meds;
    public TrackingFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.symptoms,container,false);
        symptoms = (ImageView) view.findViewById(R.id.symp_button);
        intake = (ImageView) view.findViewById(R.id.intake_button);
        pollen = (ImageView) view.findViewById(R.id.pollen_button);
        today = (ImageView) view.findViewById(R.id.today);
        meds = (ImageView) view.findViewById(R.id.meds_button);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TrackDay.class));
            }
        });
        pollen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Pollen.class));
            }
        });
        meds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Meds.class));
            }
        });
        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what to do when I click on symptoms button
                startActivity(new Intent(getActivity(),track_symptoms.class));
            }
        });
        intake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what to do when I click on on intake button
                startActivity(new Intent(getActivity(),Intake.class));
            }
        });
        return view;
    }
}
