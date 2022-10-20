package com.clase.recycleranimado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView NewsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;


    //para el modo oscudo
    FloatingActionButton fabswitcher;
    boolean isDark = false;

    //para el edittext buscar
    RelativeLayout rootLayout;
    EditText searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lets make this activity on full screen
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
           //     WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //esconder el actionbar
        getSupportActionBar().hide();



        //ini dark
        fabswitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);

        //ini search
        searchInput = findViewById(R.id.SearchInput);


        //ini view

        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        //para modo dark
        isDark = getThemeStatePref();
        if (isDark){

            searchInput.setBackgroundResource(R.drawable.search_input_darkst);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        }else{
            searchInput.setBackgroundResource(R.drawable.search_input_st);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }


        //fill list news with data
        //just for testing purpose i will fill the news list with random data
        //you may get your data from an api / firebase or sqlite database


        mData.add(new NewsItem("Tarjeta de Video RTX 2080ti","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));
        mData.add(new NewsItem("Laptop Thinkpad","e release of Letraset sheets containing Lorem Ipsum passages, and more ike Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));
        mData.add(new NewsItem("Audifoonos","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));
        mData.add(new NewsItem("Brasil","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.mexico));
        mData.add(new NewsItem("australia","e release of Letraset sheets containing Lorem Ipsum passages, PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.muralla));
        mData.add(new NewsItem("Recursos varios","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));
        mData.add(new NewsItem("mouse gamer","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.muralla));
        mData.add(new NewsItem("Egipto","e release of Letraset sheets containing Lorem Ipsum passages, of Lorem Ipsum","6 july 2020",R.drawable.mexico));
        mData.add(new NewsItem("Parlantes","e release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));
        mData.add(new NewsItem("Cpu gamer","e release of Letraset sheets containing Lorem Ipsum passages, and more like Aldus PageMaker including versions of Lorem Ipsum","6 july 2020",R.drawable.brasil));



        //Adapter ini and setup

        newsAdapter = new NewsAdapter(this,mData,isDark);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));



        //en el boton flotante agregamos funcionalidad para el darkmode
        fabswitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark;

                if (isDark){

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    searchInput.setBackgroundResource(R.drawable.search_input_darkst);

                }else{

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    searchInput.setBackgroundResource(R.drawable.search_input_st);

                }

                newsAdapter = new NewsAdapter(getApplicationContext(),mData,isDark);
                NewsRecyclerview.setAdapter(newsAdapter);
                SaveThemeStatePref(isDark);

            }
        });


        //Para el filtro de busqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                newsAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }



    private void SaveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();

    }




    private Boolean getThemeStatePref(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false);
        return isDark;

    }

}
