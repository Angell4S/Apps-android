package com.clase.uberclone.providers;

import com.clase.uberclone.models.Client;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {

    DatabaseReference mDatabase;

    public ClientProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    public Task<Void> create(Client client){
        //si no quiero que aparesca el atriburo id en mi diagrama de usuario
        Map<String, Object> map = new HashMap<>();
        map.put("name",client.getName());
        map.put("email",client.getEmail());
        return mDatabase.child(client.getId()).setValue(map);

    }

}
