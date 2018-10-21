package com.shuzzy.junaid.shuzzy;

import com.google.firebase.auth.FirebaseAuth;

public class signout {
    public signout() {
        FirebaseAuth.getInstance().signOut();
    }
}
