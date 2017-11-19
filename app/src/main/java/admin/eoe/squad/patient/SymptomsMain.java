package admin.eoe.squad.patient;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by admin on 29/06/2017.
 */

public class SymptomsMain extends AppCompatActivity{
    private TextView symtoms,bf,lunch,dinner;
    private DatabaseReference database;
    public static HashMap<String,Object> links;
    private StorageReference mStorageRef;
    private static String[] PERMISSIONS_STORAGE={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptomsmain);
        links = new HashMap<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        symtoms = (TextView) findViewById(R.id.symptoms_tv);
        bf = (TextView) findViewById(R.id.tv_bf);
        lunch = (TextView) findViewById(R.id.tv_lunch);
        dinner = (TextView) findViewById(R.id.tv_dinner);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        verifyPermissions(SymptomsMain.this);
        database.child("tracksymptoms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder string =new StringBuilder();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    string.append(data.getValue(String.class));
                    string.append("  ");
                }
                symtoms.setText(string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.child("breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    StringBuilder string = new StringBuilder();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        string.append(data.getValue(String.class));
                        string.append("  ");
                    }
                    if(string.length()!=0) {
                        bf.setText(string);
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.child("breakfast").setValue(null);
        database.child("lunch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                StringBuilder string = new StringBuilder();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    string.append(data.getValue(String.class));
                    string.append("  ");
                }
                if(string.length()!=0) {
                    lunch.setText(string);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.child("lunch").setValue(null);
        database.child("dinner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                StringBuilder string = new StringBuilder();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    string.append(data.getValue(String.class));
                    string.append("  ");
                }
                if(string.length()!=0) {
                    dinner.setText(string);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.child("dinner").setValue(null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sym);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Uri uri = getUri(takeScreenshot());
                StorageReference sympRef = mStorageRef.child("sym/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+getDate());
                sympRef.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Storing....",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Sorry..could not store in the database",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Successfully stored",Toast.LENGTH_SHORT).show();
                    }
                });
                finish();







            }
        });
    }
    private static void verifyPermissions(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,1);
        }
    }
    String refDate;
    private File takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd", now);
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");

        refDate = format.format(now);

        File imageFile = null;
        try {
            String folder = "SymptomImg";
            File f = new File(Environment.getExternalStorageDirectory(),folder);
            if(!f.exists()){
                f.mkdirs();
            }
            String mPath = Environment.getExternalStorageDirectory().toString() +"/"+"SymptomImg"+ "/" + now + ".jpg";
            View v1 = getWindow().getDecorView().getRootView();

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
    private String getDate(){
    return String.valueOf(refDate);
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
}