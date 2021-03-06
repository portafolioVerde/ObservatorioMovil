package com.example.usuario.app.myroodent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * La clase SplashActivity.java se encarga de mostrar unos segundos un splash que contiene
 * los logos e iconos de los colaboradores y ademas
 */
public class SplashActivity extends AppCompatActivity {

    TextView tDireccion,tLat,tLng;
    //@BindView(R.id.tDireccion) TextView tDireccion;
    //@BindView(R.id.tvLat) TextView tLat;
    //@BindView(R.id.tvLon) TextView tLng;
    public SharedPreferences sharedPref;
    public LocationManager ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Este metodo bloquea la rotación de la pantalla

        tDireccion = findViewById(R.id.tDireccion);
        tLat = findViewById(R.id.tvLat);
        tLng = findViewById(R.id.tvLon);

        checkLocationPermission(); //Chekea si se encuentran activos los permisos de localización
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        //ButterKnife.bind(this); //Referencia de la libreria ButterKnife
        localizacion();
        registrarLocalizacion(); //Obtiene coordenadas y convierte en direccion los datos

/**
 * El siguiente metodo run se encarga de
 * temporizar cuanto tarda en splash mostrandose
 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    /**
     * Gestiona los permisos COARSE & FINE LOCATION y ejecuta
     * los servicios de ubicación desde la primer pantalla que ve el usuario,
     * intenta obtener la ultima ubicación y las mas cercana
     */
    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    //Metodo que actualiza ubicacion en tiempo real
    private void registrarLocalizacion() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, new SplashActivity.milocalizacionListener());
    }

    /**
     * milocalizacionListener es el metodo que hace que las coordenadas
     * que se obtienen se conviertan en una direccion o en una AddressLine
     * también guarda las ubicaciones constantemente en el fichero de
     * SharedPreferences para mostrarlos en caso que el valor de la
     * tDireccion,tLat,Tlng sean null.
     */
    private class milocalizacionListener implements LocationListener {
        @Override
        @Optional
        public void onLocationChanged(Location location) {

            //System.out.println("La direccion ha cambiado");
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {

                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                tDireccion.setText(direccion.get(0).getAddressLine(0));

                Double lat = location.getLatitude();
                String latS = String.valueOf(lat);
                tLat.setText(latS);

                Double lng = location.getLongitude();
                String lngS = String.valueOf(lng);
                tLng.setText(lngS);

                Context context = getApplicationContext();

                sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.direccion_key), direccion.get(0).getAddressLine(0));
                editor.putString("latitudG",latS);
                editor.putString("longitudG",lngS);
                editor.apply();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * checkLocationPermission se encarga de validar
     * si los permisos de localización se habilitaron
     */
    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
