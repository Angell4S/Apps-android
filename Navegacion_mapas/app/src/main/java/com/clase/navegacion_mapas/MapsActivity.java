package com.clase.navegacion_mapas;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker mkprueba0,mkprueba1,mkprueba2,mkprueba3,mkprueba4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng mihouse = new LatLng(-11.90, -77.07);
        mkprueba4=googleMap.addMarker(new MarkerOptions().position(mihouse).title("Bar Rock").snippet("Las mejores tocadas").icon(BitmapDescriptorFactory.fromResource(R.drawable.rock)));

        //agregando otra ubicacion
        LatLng mihouse2 = new LatLng(-11.78, -77.15);
        mkprueba0=googleMap.addMarker(new MarkerOptions().position(mihouse2).title("Estadio nacional").snippet("El mejor estadio").icon(BitmapDescriptorFactory.fromResource(R.drawable.esta2)));

        LatLng mihouse3 = new LatLng(-11.80, -77.20);
        mkprueba1=googleMap.addMarker(new MarkerOptions().position(mihouse3).title("Estadio Puente piedra").snippet("Gallo de oro").icon(BitmapDescriptorFactory.fromResource(R.drawable.puentepiedra)));

        LatLng mihouse4 = new LatLng(-11.90, -77.30);
        mkprueba2=googleMap.addMarker(new MarkerOptions().position(mihouse4).title("rockys").snippet("La mas rica").icon(BitmapDescriptorFactory.fromResource(R.drawable.rockys)));

        LatLng mihouse5 = new LatLng(-11.85, -76.94);
        mkprueba3=googleMap.addMarker(new MarkerOptions().position(mihouse5).title("norkys").snippet("La mas sabrosa").icon(BitmapDescriptorFactory.fromResource(R.drawable.norkys)));

        //hacer zoom a la camara del mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mihouse,10));

        //Eventos ejecutables
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mkprueba0)){
            Toast.makeText(this, "Presionaste prueba0", Toast.LENGTH_SHORT).show();
        }else if (marker.equals(mkprueba1)){
            Toast.makeText(this, "Presionaste prueba1", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        if (marker.equals(mkprueba4)){
            startActivity(new Intent(MapsActivity.this, rockActivity.class));
        }else if (marker.equals(mkprueba0)){
           startActivity(new Intent(MapsActivity.this, nacionalActivity.class));
        }else if (marker.equals(mkprueba1)){
            startActivity(new Intent(MapsActivity.this, galloActivity.class));
        }else if (marker.equals(mkprueba2)){
            startActivity(new Intent(MapsActivity.this, rockysActivity.class));
        }else if (marker.equals(mkprueba3)){
            startActivity(new Intent(MapsActivity.this, norkysActivity.class));
        }

    }
}
