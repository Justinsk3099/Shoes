package com.shuzzy.junaid.shuzzy;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.MimeTypeFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.UrlEscapers;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class SellerActivity extends Fragment {

    StorageReference storageReference;
    DatabaseReference databaseReference;

    ImageView imageView;
    TextView textView;
    EditText getColor1,getBrand,getPrice;
    Button Submit;
    String URLref;

    ProgressBar progressBar;

    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    Uri picUri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_seller, container, false);


        storageReference=FirebaseStorage.getInstance().getReference("products");
        databaseReference=FirebaseDatabase.getInstance().getReference("Shoes");

        progressBar=view.findViewById(R.id.progressBar);
        imageView=view.findViewById(R.id.smpl);
        textView=view.findViewById(R.id.textView);
        getBrand=view.findViewById(R.id.getBrand);
        getPrice=view.findViewById(R.id.getPrice);
        getColor1=view.findViewById(R.id.getColor1);
        Submit=view.findViewById(R.id.button);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(getBrand.getText().toString().isEmpty()||getColor1.getText().toString().isEmpty()||getPrice.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"don't leave details empty",Toast.LENGTH_LONG).show();
                    return;
                }
                uploadImg();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBrowse();
            }
        });

        return view;

    }


    private void uploadImg() {
        if(picUri!=null){
            final StorageReference fileref=storageReference.child(System.currentTimeMillis()+".jpg");
            fileref.putFile(picUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(getActivity(),"uploaded",Toast.LENGTH_LONG).show();
                            new Upload_image(getActivity(),getBrand.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            Toast.makeText(getActivity(),"its:- "+taskSnapshot.getDownloadUrl().toString(),Toast.LENGTH_LONG).show();
                            new add_product_to_realtime(getActivity(),getBrand.getText().toString(),getColor1.getText().toString(),getPrice.getText().toString(),taskSnapshot.getDownloadUrl().toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(getActivity(),"no file seen",Toast.LENGTH_LONG).show();
        }

    }


    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){

                picUri = data.getData();

                filePath = getPath(picUri);

                if (picUri != null) {
                    Log.d("picUri", picUri.toString());
                }
                else {
                    Toast.makeText(getActivity(), "nulla", Toast.LENGTH_LONG).show();
                }
                Log.d("filePath", filePath);
                textView.setVisibility(View.GONE);
                imageView.setImageURI(picUri);

            }

        }

    }
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        else{
            Toast.makeText(getActivity(),"coloumn nulla",Toast.LENGTH_LONG).show();
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}
