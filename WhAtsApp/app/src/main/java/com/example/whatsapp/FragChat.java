package com.example.whatsapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragChat extends Fragment implements RecyclerViewAdapterChat.OnChatListener {

    View v;
    private RecyclerView myrecyclerview;
    private List<ConstChat> lstChat;





    public FragChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_chat, container, false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.chat_rcv);
        RecyclerViewAdapterChat rcvChat = new RecyclerViewAdapterChat(getContext(),lstChat,this);
       myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        myrecyclerview.setAdapter(rcvChat);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        lstChat = new ArrayList<>();
        lstChat.add(new ConstChat("Angel ","Asencios Mory",R.drawable.house2));
        lstChat.add(new ConstChat("Juan ","Alvarez Arango",R.drawable.m4));
        lstChat.add(new ConstChat("Manuel ","Chaverra Cardenas",R.drawable.m3));
        lstChat.add(new ConstChat("Antuanet ","Atehortua Agudelo",R.drawable.m5));
        lstChat.add(new ConstChat("Andre ","Piedrahita Cardona",R.drawable.m6));
        lstChat.add(new ConstChat("Gabriela ","Blandon Gómez",R.drawable.m14));
        lstChat.add(new ConstChat("Carlos ","Grisales Barrientos",R.drawable.m8));
        lstChat.add(new ConstChat("Javier ","Gaviria Zapata",R.drawable.m9));
        lstChat.add(new ConstChat("Alonso ","Pérez Fernandez",R.drawable.m10));




    }



    @Override
    public void OnChatClick(int position) {


        Intent intent=new Intent(getContext(),chatMessage.class);
        Toast.makeText(getContext(),"Estas en el chat " + position,Toast.LENGTH_LONG).show();
        intent.putExtra("nombre",  lstChat.get(position));
        intent.putExtra("apellidos",  lstChat.get(position));
        intent.putExtra("foto",lstChat.get(position));

        startActivity(intent);
    }
}
