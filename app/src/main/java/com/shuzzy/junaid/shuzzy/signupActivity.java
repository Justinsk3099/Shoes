package com.shuzzy.junaid.shuzzy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class signupActivity extends AppCompatActivity {


    EditText username,password,conpassword;
    Button btnsubmit,haveaccount;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final int RC_SIGN_IN = 9001;

    GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        btnsubmit=findViewById(R.id.btnsubmit);
        haveaccount=findViewById(R.id.haveaccount);

        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(username.getText().toString(),password.getText().toString());

            }
        });

        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupActivity.this,signinActivity.class));
            }
        });

        signInButton=findViewById(R.id.signInButton);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(signupActivity.this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
//email signup START
    public boolean validation(String username1,String password1){
        boolean valid=true;


        if (username1.length()<=8||username1.length()>16){
            Toast.makeText(signupActivity.this,"Enter a valid username of length 8-16 ",Toast.LENGTH_LONG).show();
            valid=false;
        }
        if (password1.length()<8||password1.length()>16){
            Toast.makeText(signupActivity.this,"Enter password of length 8-16 letter",Toast.LENGTH_LONG).show();
            valid=false;
        }

        return valid;
    }

    private void createAccount(String email, String password) {
        Log.d("EMAIL signup", "createAccount:" + email);
        if (!validation(email,password)) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("EMAIL signup", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                                editor.putString("email",user.getEmail());
                                editor.putString("uid",user.getUid());
                                editor.commit();
                                startActivity(new Intent(signupActivity.this,signinActivity.class));


                        } else {

                            Log.w("EMAIL signup", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
//email signup END

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(signupActivity.this,Navigation.class));
        }
    }

    //google Signin START
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("googleSignin", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(signupActivity.this,MainActivity.class));

                            Log.d("GoogleSignin", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("GoogleSignin", "signInWithCredential:failure", task.getException());
                            Toast.makeText(signupActivity.this,"Authentication failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser!=null){
                        editor.putString("uid",currentUser.getUid());
                    }
                    editor.putString("email", account.getEmail());
                    editor.commit();
                }
            } catch (ApiException e) {
                Log.w("Googlesignin", "Google sign in failed", e);
            }
        }



    }
    //google Signin END

}
