package com.clase.blogapp.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clase.blogapp.Fragments.HomeFragment;
import com.clase.blogapp.Fragments.ProfileFragment;
import com.clase.blogapp.Fragments.SettingsFragment;
import com.clase.blogapp.Models.Post;
import com.clase.blogapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;

    private static final int REQUESCODE = 2 ;
    private static final int PReqCode = 2;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Dialog popAddpost;
    ImageView popUserimg,popPostimg,popAddbtn;
    TextView popTitle,popDescrip;
    ProgressBar popClickProgress;
    private Uri pickedImgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ini
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        //ini popup
        iniPopup();
        setupPopupImageClick();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               popAddpost.show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Pasar cada ID de menú como un conjunto de ID porque cada
        //menú debe considerarse como destinos de nivel superior.

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //esto se usa cuando usas navigation
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navi_home, R.id.navi_profile, R.id.navi_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.container);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/



        updateNavHeader();


        //establece el fragmento como el predeterminado

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

    }

    private void setupPopupImageClick() {
        popPostimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aquí, cuando se hace clic en la imagen, necesitamos abrir la galería
                // antes de abrir la galería, debemos verificar que nuestra aplicación tenga acceso a los archivos de usuario
                //hicimos esto antes en la actividad de registro, solo voy a copiar el código para ahorrar tiempo

                checkAndRequestForPermission();

            }
        });
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(Home.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            }else{
                ActivityCompat.requestPermissions(Home.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }

        }else
            //si todo sale bien tenemos permiso para acceder a la galera de usuario
            openGallery();
    }

    private void openGallery() {
        //Todo:open galley intent and wait for user top ick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }


    //when user picked an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            //el usuario ha elegido con éxito una imagen
            //necesitamos guardar su referencia a una variable Uri

            pickedImgUri = data.getData();
            popPostimg.setImageURI(pickedImgUri);


        }

    }


    private void iniPopup() {

        popAddpost = new Dialog(this);
        popAddpost.setContentView(R.layout.popup_add_post);
        popAddpost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddpost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddpost.getWindow().getAttributes().gravity = Gravity.TOP;

        //ini pop widwets
        popUserimg=popAddpost.findViewById(R.id.pop_userimg);
        popPostimg=popAddpost.findViewById(R.id.popup_postimg);
        popTitle=popAddpost.findViewById(R.id.titlepop);
        popDescrip=popAddpost.findViewById(R.id.descripop);
        popAddbtn=popAddpost.findViewById(R.id.popup_add);
        popClickProgress=popAddpost.findViewById(R.id.popup_progress);

        //load current user profile foto
        Glide.with(Home.this).load(currentUser.getPhotoUrl()).into(popUserimg);



        //add post click listener
        popAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddbtn.setVisibility(View.INVISIBLE);
                popClickProgress.setVisibility(View.VISIBLE);

                //necesitamos probar todos los campos de entrada (Título y descripción) y publicar la imagen

                if (!popTitle.getText().toString().isEmpty() && !popDescrip.getText().toString().isEmpty() && pickedImgUri != null){

                    // siempre está okey sin valor vacío o nulo
                    // TODO Crear objeto de publicación y agregarlo a la base de datos de Firebase
                    // primero necesitamos subir la imagen de la publicación
                    // acceder al almacenamiento de firebase

                    StorageReference  storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");
                    final StorageReference  imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    //Create post Object
                                    Post post = new Post(popTitle.getText().toString(),
                                                        popDescrip.getText().toString(),
                                                        imageDownloadLink,
                                                        currentUser.getUid(),
                                                        currentUser.getPhotoUrl().toString());

                                    //Add post to firebase database

                                    addPost(post);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //por si algo sale mal al subir la foto

                                    showMessage(e.getMessage());
                                    popClickProgress.setVisibility(View.INVISIBLE);
                                    popAddbtn.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                    });



                }else{
                    showMessage("Please verify all input fields and choose post image");
                    popAddbtn.setVisibility(View.VISIBLE);
                    popClickProgress.setVisibility(View.INVISIBLE);
                }

            }
        });



    }

    private void addPost(Post post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        //obtener ID único del post y actualizar clave de publicación
        String key = myRef.getKey();
        post.setPostKey(key);

        //agregar datos del post a la base de datos de Firebase

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added successfully");
                popClickProgress.setVisibility(View.INVISIBLE);
                popAddbtn.setVisibility(View.VISIBLE);
                popAddpost.dismiss();
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate el menú; esto agrega elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.nav_Home){
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }else if (id == R.id.nav_Profile){
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
        }else if (id == R.id.nav_Settings){
            getSupportActionBar().setTitle("Setting");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new SettingsFragment()).commit();
        }else if (id == R.id.nav_signout){

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserMail = headerView.findViewById(R.id.nav_usermail);
        ImageView navUserFoto=headerView.findViewById(R.id.nav_userImg);

        navUserMail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        //ahora usaremos glide para cargar la imagen del usuario
        //primero necesitamos importar la biblioteca de Glide

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserFoto);

    }



}
