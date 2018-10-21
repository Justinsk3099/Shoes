package com.shuzzy.junaid.shuzzy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class addtowishlist {
    DatabaseReference db;
    DataSnapshot ds;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMicro;

    getItems getItems;

    public addtowishlist(Context context, final String refkey)
    {
        sharedPreferences = context.getSharedPreferences ("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getItems=new getItems();

        db = FirebaseDatabase.getInstance().getReference("wishlist").child(sharedPreferences.getString("uid", "null")).child(refkey);

        mDatabase = FirebaseDatabase.getInstance().getReference("Shoes").child(refkey);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {



                    getItems = ds.getValue(getItems.class);
                    String dsbrand = getItems.getBrand();
                    String dscolor = getItems.getColor();
                    String dsprice = getItems.getDescription();
                    String dsurl = getItems.getUri();


//                    String dsbrand = ds.child(refkey).child("brand").getValue().toString();
//                    String dscolor = ds.child(refkey).child("color").getValue().toString();
//                    String dsprice = ds.child(refkey).child("price").getValue().toString();
//                    String dsurl = ds.child(refkey).child("url").getValue().toString();



                    db.child("brand").setValue(dsbrand);
                    db.child("color").setValue(dscolor);
                    db.child("price").setValue(dsprice);
                    db.child("url").setValue(dsurl);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}