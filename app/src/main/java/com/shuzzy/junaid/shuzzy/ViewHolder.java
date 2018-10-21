package com.shuzzy.junaid.shuzzy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    io.opencensus.stats.View view;




    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView=itemView;

        itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mClickListner.onItemClick (view,getAdapterPosition ());
            }
        });

    }
    public void setDetails(MainActivity context, String name, String color, String price, String image) {

        TextView tvname=mView.findViewById(R.id.tvname);
        TextView tvprice=mView.findViewById(R.id.tvprice);
        ImageView prodimg=mView.findViewById(R.id.prodimg);

        tvname.setText(name+" "+color);
        tvprice.setText(price);
        Picasso p=new Picasso.Builder(context.getActivity()).memoryCache(new LruCache(240000)).build();
       p.load(image).resize(1080,1080).centerInside().placeholder(R.drawable.progress_animation).error(R.drawable.error_icon).into(prodimg);


    }

    public interface ClickListner{
        void onItemClick(View view,int position);
    }
    ViewHolder.ClickListner mClickListner;
    public void setOnClickListner(ViewHolder.ClickListner clickListner){
        mClickListner=clickListner;
    }

    public void setDetails(cartActivity context, String name,String color, String price, String image) {

        TextView tvname=mView.findViewById(R.id.tvname);
        TextView tvprice=mView.findViewById(R.id.tvprice);
        ImageView prodimg=mView.findViewById(R.id.prodimg);
        final TextView countr=mView.findViewById (R.id.countr);
        Button Incre=mView.findViewById (R.id.incre);
        Button decre=mView.findViewById (R.id.decre);


        tvname.setText(name+" "+color);
        tvprice.setText(price);
        Picasso p=new Picasso.Builder(context).memoryCache(new LruCache(240000)).build();
        p.load(image).resize(1080,1080).centerInside().placeholder(R.drawable.progress_animation).error(R.drawable.error_icon).into(prodimg);

        Incre.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                countr.setText(String.valueOf ((Integer.valueOf(String.valueOf (countr.getText())))+1));
            }
        });
        decre.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                countr.setText(String.valueOf ((Integer.valueOf(String.valueOf (countr.getText())))-1));
            }
        });

    }
    public void setDetails(final wishlistActivity context, String name, String color, String price, String image,String refer) {

        TextView tvname=mView.findViewById(R.id.tvname);
        TextView tvprice=mView.findViewById(R.id.tvprice);
        ImageView prodimg=mView.findViewById(R.id.prodimg);
        Button addtocartbtn=mView.findViewById (R.id.addtocartwish);

        tvname.setText(name+" "+color);
        tvprice.setText(price);
        Picasso p=new Picasso.Builder(context).memoryCache(new LruCache(240000)).build();
        p.load(image).resize(1080,1080).centerInside().placeholder(R.drawable.progress_animation).error(R.drawable.error_icon).into(prodimg);

        addtocartbtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //new addtocart (context,refer);
            }
        });

    }

}
