package admin.eoe.squad.patient;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 23/07/2017.
 */

public class DocsTreatFrag extends android.support.v4.app.Fragment {
    private TextView diet,medication,supplements,allergies,othercond;
    private TextView appointments,name,score;
    private ImageView setting;
    private DatabaseReference database,database1;
    private ImageView save;
    private
    int x;
    private StorageReference mStorageRef;
    private View layout;
    private Button add;
    private String user;
    private static String[] PERMISSIONS_STORAGE={android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.treatment_main
                ,container,false);
        layout = (View) view.findViewById(R.id.toolbar_);
        layout.setVisibility(View.GONE);
        diet = (TextView) view.findViewById(R.id.diet_lv);
        medication = (TextView) view.findViewById(R.id.medic_lv);
        supplements = (TextView) view.findViewById(R.id.supp_lv);
        allergies = (TextView) view.findViewById(R.id.allergy_lv);
        othercond = (TextView) view.findViewById(R.id.other_lv);
        appointments = (TextView) view.findViewById(R.id.appoint_lv);
        save = (ImageView) view.findViewById(R.id.fab_treat);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        add = (Button) view.findViewById(R.id._add_button);
        add.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TreatmentOverveiw.class));
            }
        });
        score = (TextView) view.findViewById(R.id.score);
        name = (TextView) view.findViewById(R.id.toolbar_name);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        database.child("currentuser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(String.class);
                database1 = FirebaseDatabase.getInstance().getReference(user);

                database1.child("score").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        int res = Integer.valueOf(s) * 100;
                        score.setText(String.valueOf(res));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> name1 = new ArrayList<String>();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            name1.add((String) data.getValue());
                        }
                        name.setText(name1.get(0));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                setting = (ImageView) view.findViewById(R.id.menu_dash);
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
                database1.child("eoesubmain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            for (DataSnapshot data1 : data.getChildren()) {
                                diet.append(data1.getValue(String.class) + "\r\n");
                            }
                            diet.append("\r\n" );
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("medicsubmain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            for (DataSnapshot data1 : data.getChildren()) {
                                medication.append(data1.getValue(String.class) + "\r\n");
                            }
                            medication.append("\r\n" );
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("suppsubmain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            for (DataSnapshot data1 : data.getChildren()) {
                                supplements.append(data1.getValue(String.class) + "\r\n");
                            }
                            supplements.append("\r\n" );
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("diet").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            allergies.append(data.getValue(String.class) + " allergy" + "\r\n");


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("othersubmain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            othercond.append(data.getValue(String.class) + "\r\n");

                            othercond.append("last updated: july 30, 2017" + "\r\n");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database1.child("nextappoi").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            appointments.append(data.getValue(String.class) + "\r\n");


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference treatRef = mStorageRef.child("treat/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg");

                Uri uri = getUri(takeScreenshot());
                treatRef.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(),"Storing...",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Sorry..not stored in database",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(),"Successfully Stored",Toast.LENGTH_SHORT).show();
                    }
                });










            }
        });
        return view;
    }
    private static void verifyPermissions(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,1);
        }
    }
    private File takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:ss", now);

        File imageFile = null;
        try {
            String folder = "MedicImg";
            File f = new File(Environment.getExternalStorageDirectory(),folder);
            if(!f.exists()){
                f.mkdirs();
            }
            String mPath = Environment.getExternalStorageDirectory().toString() +"/"+"MedicImg"+ "/" + now + ".jpg";
            View v1 = getActivity().getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());

            imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            openScreenshot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
    private void openScreenshot(File imageFile){
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri,"image/*");
        startActivity(intent);
    }
    private Uri getUri(File imageFile){
        return Uri.fromFile(imageFile);
    }
    private String getImageExt(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
