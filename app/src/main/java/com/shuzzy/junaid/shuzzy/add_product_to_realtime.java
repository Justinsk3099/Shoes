package com.shuzzy.junaid.shuzzy;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class add_product_to_realtime {

    Date date = new Date();
    DatabaseReference db;

    public add_product_to_realtime(final Context context, String brand, String color, String price, String url) {

        DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmms");
        String strDate = dateFormat.format(date);

        final String datea=strDate;

        db = FirebaseDatabase.getInstance().getReference("Shoes");

        db.child(brand+datea).child("brand").setValue(brand);
        db.child(brand+datea).child("color").setValue(color);
        db.child(brand+datea).child("price").setValue(price);
        db.child(brand+datea).child("url").setValue(url);

    }

}
