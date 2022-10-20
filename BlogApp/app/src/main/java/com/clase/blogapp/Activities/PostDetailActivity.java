package com.clase.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clase.blogapp.Adapters.CommentAdapter;
import com.clase.blogapp.Models.Comment;
import com.clase.blogapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PostDetailActivity extends AppCompatActivity {

        ImageView imgPost,imgUserPost,imgCurrentUser;
        TextView txtPostDesc,txtPostDateName,txtPostTitle;
        EditText edtComment;
        Button btnAddcomend;
        String PostKey;

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;

        FirebaseDatabase firebaseDatabase;

        RecyclerView RvComment;
        CommentAdapter commentAdapter;
        List<Comment> listComment;

        static String COMMENT_KEY = "Commend";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //establezcamos la barra de estado en transparente
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();


        //ini Views
        RvComment = findViewById(R.id.rv_comment);
        imgPost = findViewById(R.id.post_detailimg);
        imgUserPost = findViewById(R.id.post_userdetail);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser);

        txtPostTitle = findViewById(R.id.post_detailtitle);
        txtPostDesc = findViewById(R.id.post_detaildescription);
        txtPostDateName = findViewById(R.id.post_detaildatename);

        edtComment = findViewById(R.id.post_edtcomment);
        btnAddcomend = findViewById(R.id.post_btnaddcoment);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        btnAddcomend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference commentReference = firebaseDatabase.getReference("").child(COMMENT_KEY).child(PostKey).push();
                String comment_content = edtComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("commend added");
                        edtComment.setText("");
                        btnAddcomend.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      showMessage("fail to add comment : "+e.getMessage());
                    }
                });


            }
        });



        // ahora necesitamos vincular todos los datos en esas vistas
        // primero necesitamos obtener datos de Post
        // primero debemos enviar datos detallados de la publicación a esta actividad ...
        // ahora podemos obtener datos de publicación



        String postImage = getIntent().getExtras().getString("postImage");
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle= getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String userpostImage = getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userpostImage).into(imgUserPost);

        String postDescription= getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);

        //set comment user image
        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);

        //Post ID
        PostKey = getIntent().getExtras().getString("postKey");

        String date = timestampToString(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);


        //ini RecyclerView Comment
        iniRvComment();


    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listComment = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    Comment  comment = snap.getValue(Comment.class);
                    listComment.add(comment);
                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }



    private static String timestampToString(long time){

        //Obtener la fecha u hora formalmente
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()).toString();
        return date;
    }


}
