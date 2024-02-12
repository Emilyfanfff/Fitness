package com.example.fitnessapp.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.MapFragmentBinding;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.viewmodel.SharedViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment {
    private MapFragmentBinding binding;
    public MapFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = MapFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        myRef.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Gson gson = new Gson();
                    User user = gson.fromJson(new Gson().toJson(task.getResult().getValue()), User.class);
                    String address = user.address;
                    double lat = 1.0;
                    double lng = 1.0;
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> addressList;
                    try {
                        addressList = geocoder.getFromLocationName(address,1);
                        if (addressList != null){
                            lat = addressList.get(0).getLatitude();
                            lng = addressList.get(0).getLongitude();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mapbox_marker_icon_20px_red);
                    final Point point = Point.fromLngLat(lng, lat );
                    CameraOptions cameraPosition = new CameraOptions.Builder()
                            .zoom(13.0)
                            .center(point)
                            .build();
                    binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
                    binding.mapView.getMapboxMap().setCamera(cameraPosition);
                    AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate)binding.mapView);
                    PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI,binding.mapView);
                    PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                            .withPoint(com.mapbox.geojson.Point.fromLngLat(lng, lat))
                            .withIconImage(bitmap);
                    pointAnnotationManager.create(pointAnnotationOptions);
                }
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
