package com.shuzzy.junaid.shuzzy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class addtocart {

    DatabaseReference db;

    DataSnapshot dataSnapshot;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMicro;

    public addtocart(final Context context, final String refkey){

        sharedPreferences = context.getSharedPreferences ("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();



        mDatabase = FirebaseDatabase.getInstance().getReference("Shoes");
        mDatabaseMicro = mDatabase.child("brand");
        mDatabaseMicro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String dsbrand = ds.child(refkey).child("brand").getValue().toString();
                    String dscolor = ds.child(refkey).child("color").getValue().toString();
                    String dsprice = ds.child(refkey).child("price").getValue().toString();
                    String dsurl = ds.child(refkey).child("url").getValue().toString();

                    db = FirebaseDatabase.getInstance().getReference("addtocart").child(user.getUid()).child(refkey);

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
