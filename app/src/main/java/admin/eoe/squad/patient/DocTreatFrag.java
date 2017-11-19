package admin.eoe.squad.patient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 13/07/2017.
 */

public class DocTreatFrag extends Fragment {
    private ImageView iv,down;
    private StorageReference mStorage;
    private DatabaseReference database;
    String suffix;
    File localFile;
    private ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_patient_treatment,container,false);
        iv= (ImageView) view.findViewById(R.id.doc_treatment);
        down = (ImageView) view.findViewById(R.id.icon_down);
        database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        progress= new ProgressDialog(getActivity());


        database.child("currentuser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suffix = dataSnapshot.getValue(String.class);
                mStorage = FirebaseStorage.getInstance().getReference().child("treat/"+suffix+".jpg");

                try {

                    localFile = File.createTempFile(suffix,".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                down.setOnClickListener(new View.OnClickListener() {
                    String url;
                    @Override
                    public void onClick(View v) {
                        mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(!uri.toString().startsWith("http://")&& !uri.toString().startsWith("https://")){
                                    url = "http://"+uri.toString();
                                }else{
                                    url = uri.toString();
                                }
                                if(url!=null) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Sorry something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                mStorage.getFile(localFile).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        progress.setMessage("The patient's treatment is being downloaded...");
                        progress.setCancelable(true);
                        progress.show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        iv.setImageBitmap(bitmap);
                        if(progress.isShowing()){
                            progress.cancel();
                            progress.dismiss();}
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Something's wrong",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
