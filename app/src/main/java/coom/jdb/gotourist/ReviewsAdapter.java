package coom.jdb.gotourist;

import android.content.Context;
import android.graphics.Color;
import android.media.Rating;
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

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private Context ctx;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        super(context,0 ,reviews);
        this.ctx = context;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Review review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_place, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvDate = (TextView) convertView.findViewById(R.id.date);
        TextView tvText = (TextView) convertView.findViewById(R.id.text);

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.list_image);

        RatingBar rbRating = (RatingBar) convertView.findViewById(R.id.rating);

        tvName.setText(review.getAuthor());
        tvDate.setText(review.getWhen());
        tvText.setText(review.getText());

        Picasso.with(ctx).load(review.getProfilePicture()).into(ivImage);

        rbRating.setNumStars((int) review.getRating());

        // Return the completed view to render on screen
        return convertView;
    }


}