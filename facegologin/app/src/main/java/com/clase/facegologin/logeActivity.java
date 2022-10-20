package com.clase.facegologin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;


public class logeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //facebook login



    //google login
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    public static final int  SIGN_IN=1;

    //login normal
    EditText lEmail, lPassword;
    Button lLogin;
    FirebaseAuth lAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loge);


        //login normal///////////////////////////////////////////////////
        lEmail = findViewById(R.id.idcorreo);
        lPassword = findViewById(R.id.idcontra);
        lLogin = findViewById(R.id.idingresar);
        lAuth = FirebaseAuth.getInstance();

        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lEmail.setError("Ingrese correo electrónico");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    lPassword.setError("Ingrese contraseña");
                    return;
                }

                if(password.length()<6){
                    lPassword.setError("Minimo 6 dígitos");
                    return;
                }

                lAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(logeActivity.this, "Bienvenido usuario", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(logeActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(logeActivity.this, "El usuario o contrase es incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////





/////////////////////////////////////////////////////////////////////////////////////////////////
        //Login con google
        /////////////////////////////////////////////////////
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        signInButton = findViewById(R.id.googlelogin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,SIGN_IN);
            }
        });








        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //Login con Facebook
        /////////////////////////////////////////////////////




    }





/////////////////////////////////////////////////////////////////////////////////////////////////










    /////////////////////////////////////////////////////////////////////////////////////////////////
    //Login con google
    /////////////////////////////////////////////////////
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                startActivity(new Intent(logeActivity.this,Main2Activity.class));
                finish();
            }else{
                Toast.makeText(this, "Fallo el inicio de sesion", Toast.LENGTH_SHORT).show();
            }
        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////






    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void SendToRegister(View view) {
        startActivity(new Intent(logeActivity.this, registroActivity.class));
    }

}
