package pong.ldz.com.ping;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Model.Host;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (HostsActivity.hosts != null)
        for (Host h : HostsActivity.hosts) {
            if (!h.latitude.isEmpty()) {
                LatLng ponto_host = new LatLng(Double.parseDouble(h.latitude), Double.parseDouble(h.longitude));
                mMap.addMarker(new MarkerOptions().position(ponto_host).title(h.nome));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ponto_host));
                builder.include(ponto_host);
            }
        }
        mMap.setMyLocationEnabled(true);
        try {
            LatLngBounds bounds = builder.build();

            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);


            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.moveCamera(cu);
                    mMap.animateCamera(cu);
                }
            });
            }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
}
