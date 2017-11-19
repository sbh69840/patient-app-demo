package admin.eoe.squad.patient;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 27/06/2017.
 */

public class HomeMain extends Fragment {
    private ImageView img,menu,main,progress,game,compil;
    private TextView text,score,name1,welcome;

    private DatabaseReference database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_main,container,false);
        final List<String> name = new ArrayList<>();
        score = (TextView) view.findViewById(R.id.score);
        menu = (ImageView ) view.findViewById(R.id.menu_dash);
        name1 = (TextView) view.findViewById(R.id.hi_name);
        main = (ImageView) view.findViewById(R.id.main_img);
        progress = (ImageView) view.findViewById(R.id.progress_img);
        game = (ImageView) view.findViewById(R.id.game_img);
        compil = (ImageView)view.findViewById(R.id.compil);
        welcome = (TextView) view.findViewById(R.id.welcome);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        database.child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
if(dataSnapshot.getValue(String.class).equals("treat")){
                    welcome.setVisibility(View.GONE);
                    name1.setVisibility(View.GONE);
                    compil.setVisibility(View.VISIBLE);
                    main.setImageResource(R.mipmap.actionbtntracking);
                    game.setImageResource(R.mipmap.icongame);
                }else if(dataSnapshot.getValue(String.class).equals("book")){
    welcome.setVisibility(View.GONE);
    name1.setVisibility(View.GONE);
    compil.setVisibility(View.VISIBLE);
    main.setImageResource(R.mipmap.actionbtnresources);

    game.setImageResource(R.mipmap.icongame);
    progress.setImageResource(R.mipmap.progressbars);

    progress.setImageResource(R.mipmap.progressbarfull);

    main.setImageResource(R.mipmap.actionbtngreatjob);


}else{
    welcome.setVisibility(View.VISIBLE);
    name1.setVisibility(View.VISIBLE);
    compil.setVisibility(View.GONE);
    main.setImageResource(R.mipmap.actionbtnmytreatment);
    game.setImageResource(R.mipmap.icongame);
    progress.setImageResource(R.mipmap.progressempty);
}
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Game1.class));


            }
        });
        database.child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                int res = Integer.valueOf(s)*100;
                score.setText(String.valueOf(res));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    name.add((String) data.getValue());
                }
                text.setText(name.get(0));
                name1.setText("Hi "+name.get(0)+",");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        text = (TextView) view.findViewById(R.id.toolbar_name);

        score = (TextView) view.findViewById(R.id.score);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }
}
