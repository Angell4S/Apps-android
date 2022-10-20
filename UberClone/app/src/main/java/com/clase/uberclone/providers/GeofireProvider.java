package com.clase.uberclone.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {

    private DatabaseReference mDatabase;
    private GeoFire mGeoFire;

    public GeofireProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("active_drivers");
        mGeoFire = new GeoFire(mDatabase);
    }

    //guardar localizacion del usuario en database realtime
    public void saveLocation(String idDriver, LatLng latLng){

        mGeoFire.setLocation(idDriver, new GeoLocation(latLng.latitude, latLng.longitude));

    }

    public void removeLocation(String idDriver){
        mGeoFire.removeLocation(idDriver);
    }


    //obtener los conductores disponibles
    //radius se utiliza para que el usuario vea los conductores a al radio que pongamos
    public GeoQuery getActiveDrivers(LatLng latLng){
        GeoQuery geoQuery = mGeoFire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 10);
        geoQuery.removeAllListeners();
        return geoQuery;
    }


}
