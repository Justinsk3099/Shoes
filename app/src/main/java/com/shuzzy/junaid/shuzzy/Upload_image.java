package com.shuzzy.junaid.shuzzy;

import android.content.Context;
import android.widget.Toast;

public class Upload_image {
    private String mName;
    private String mImageURL;

    public Upload_image(){
        //NEEDED
    }

    public Upload_image(Context context,String name, String imageUrl){
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageURL = imageUrl;
    }

    public String getmNmae() {
        return mName;
    }

    public void setmNmae(String mNmae) {
        this.mName = mNmae;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
