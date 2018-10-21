    package com.shuzzy.junaid.shuzzy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

    public class viewprodActivity extends AppCompatActivity {

    TextView tvnameview,tvpriceview;
    ImageView proimageview;
    Button addtocartbtn,addtowishlistbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_viewprod);

        tvnameview=findViewById (R.id.tvnameview);
        tvpriceview=findViewById (R.id.tvpriceview);
        proimageview=findViewById (R.id.prodimgview);
        addtocartbtn=findViewById (R.id.addtocartbtn);
        addtowishlistbtn=findViewById (R.id.addtowishlistbtn);

        byte[] bytes=getIntent ().getByteArrayExtra ("image");
       final String refrenc=getIntent ().getStringExtra ("reference");
        String nameview=getIntent ().getStringExtra ("name");
        String priceview=getIntent ().getStringExtra ("price");
        String urlview=getIntent ().getStringExtra ("url");
        Bitmap bm=BitmapFactory.decodeByteArray (bytes,0,bytes.length);

        tvnameview.setText (nameview);
        tvpriceview.setText (priceview);
        proimageview.setImageBitmap (bm);

        addtocartbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                new addtocart(viewprodActivity.this,refrenc);
            }
        });

        addtowishlistbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                new addtowishlist (viewprodActivity.this,refrenc);

            }
        });

    }

}
