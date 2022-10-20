package com.example.whatsapp;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterChat extends RecyclerView.Adapter<RecyclerViewAdapterChat.MyViewHolder> {


    Context mContext;
    List<ConstChat> mData;
    Dialog myDialog;
    OnChatListener mOnChatListener;


    public RecyclerViewAdapterChat(Context mContext, List<ConstChat> mData,OnChatListener onChatListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnChatListener= onChatListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


       View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.item_chat,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v,mOnChatListener);

      /* myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.activity_chat_message);*/
         /*myDialog.getWindow().setFlags(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);*/




       /* vHolder.item_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




        Toast.makeText(mContext,"Estas en el chat "+ String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_LONG).show();
               Intent i = new Intent(mContext,chatMessage.class);

                mContext.startActivity(i);

              TextView dialog_nametv = (TextView)myDialog.findViewById(R.id.nom_mess);
                TextView dialog_apetv = (TextView)myDialog.findViewById(R.id.ape_mess);
                ImageView dialog_chatimg=(ImageView)myDialog.findViewById(R.id.img_chat);
                Button btnCall=(Button)myDialog.findViewById(R.id.btn_dialogcall);
                Button btnMessa=(Button)myDialog.findViewById(R.id.btn_dialogmessage);
               dialog_nametv.setText(mData.get(vHolder.getAdapterPosition()).getNom());
                dialog_apetv.setText(mData.get(vHolder.getAdapterPosition()).getApe());
                dialog_chatimg.setImageResource(mData.get(vHolder.getAdapterPosition()).getFoto());


                myDialog.show();

               /*btnMessa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext,chatMessage.class);
                        mContext.startActivity(i);
                    }
                });*/




           /* }
        });*/

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.tv_nom.setText(mData.get(position).getNom());
        holder.tv_ape.setText(mData.get(position).getApe());
        holder.img.setImageResource(mData.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /*private LinearLayout item_chat;*/
        private TextView tv_nom;
        private TextView tv_ape;
        private ImageView img;
        OnChatListener onChatListener;

        public MyViewHolder(View itemView,OnChatListener onChatListener) {
            super(itemView);

            /*item_chat = (LinearLayout)itemView.findViewById(R.id.chat_item_id);*/
            tv_nom = (TextView) itemView.findViewById(R.id.nom_Chat);
            tv_ape = (TextView) itemView.findViewById(R.id.ape_Chat);
            img = (ImageView) itemView.findViewById(R.id.img_chat);
            this.onChatListener = onChatListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onChatListener.OnChatClick(getAdapterPosition());
        }
    }

    public interface OnChatListener {
        void OnChatClick(int position);
    }

}
