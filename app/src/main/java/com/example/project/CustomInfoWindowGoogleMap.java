package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.data.model.InfoWindowData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_window, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView description_tv = view.findViewById(R.id.description);
        ImageView img = view.findViewById(R.id.pic);
        TextView price_tv = view.findViewById(R.id.price);
        TextView type_tv = view.findViewById(R.id.type);

        name_tv.setText(marker.getTitle());
        description_tv.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

//        int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
//                "drawable", context.getPackageName());
        img.setImageResource(R.drawable.ipad);

        price_tv.setText(infoWindowData.getPrice());
        type_tv.setText(infoWindowData.getType());

        return view;
    }
}
