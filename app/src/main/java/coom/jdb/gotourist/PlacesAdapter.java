package coom.jdb.gotourist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import coom.jdb.gotourist.Place;

public class PlacesAdapter extends ArrayAdapter<Place> {

    private Context ctx;
    private ArrayList<Place> places;

    public PlacesAdapter(Context context, ArrayList<Place> places) {
        super(context, 0, places);
        this.ctx = context;
        this.places = places;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        RatingBar rbRating = (RatingBar) convertView.findViewById(R.id.rating);
        TextView tvDistance = (TextView) convertView.findViewById(R.id.distance);
        TextView tvOpen = (TextView) convertView.findViewById(R.id.open);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.list_image);


        //
        String distance = String.valueOf(String.format("%.2f", place.getDistance()) + " miles");
        // Populate the data into the template view using the data object
        tvName.setText(place.getName());
        tvDistance.setText(distance);

        if(place.getOpen().toString().equals("Open Now")){
            tvOpen.setTextColor(Color.parseColor("#3bdb50"));
        } else if(place.getOpen().toString().equals("Closed")){
            tvOpen.setTextColor(Color.parseColor("#db3b55"));
        } else {
            tvOpen.setTextColor(Color.parseColor("#db9b3b"));
        }

        tvOpen.setText(place.getOpen().toString());


        if(place.getRating() == 0){
            rbRating.setVisibility(View.GONE);
        } else {
            rbRating.setNumStars((int) place.getRating());
        }

        String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + place.getImageUrl() + "&key=AIzaSyAFKztwuiyP2noQlGXkRpDohUejTLLb4Kk";
        Picasso.with(ctx).load(imageUrl).into(ivImage);
        // Return the completed view to render on screen
        return convertView;
    }

    public Place getItemAtPosition(int i){
        return places.get(i);
    }


}