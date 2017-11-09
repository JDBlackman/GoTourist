package coom.jdb.gotourist;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PlaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String id;
    private static ArrayList<Review> arrayOfReviews = new ArrayList<Review>();
    private static ReviewsAdapter adapter = null;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b != null) {
            id = (String) b.get("place_id");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=AIzaSyAFKztwuiyP2noQlGXkRpDohUejTLLb4Kk", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject resultObj = response.getJSONObject("result");
                    Log.d("Test", resultObj.getString("name").toString());

                   arrayOfReviews = new ArrayList<Review>();

                    Location location = new Location("");
                    location.setLatitude(Double.parseDouble(resultObj.getJSONObject("geometry").getJSONObject("location").getString("lat")));
                    location.setLongitude(Double.parseDouble(resultObj.getJSONObject("geometry").getJSONObject("location").getString("lng")));

                    JSONArray arrayReviews = resultObj.getJSONArray("reviews");
                    for(int i = 0;i < arrayReviews.length();i++){

                        //String author, String profilePicture, String when, String text, double rating
                        Review review = new Review(arrayReviews.getJSONObject(i).get("author_name").toString(), arrayReviews.getJSONObject(i).get("profile_photo_url").toString(), arrayReviews.getJSONObject(i).get("relative_time_description").toString(), arrayReviews.getJSONObject(i).get("text").toString(), Double.parseDouble(arrayReviews.getJSONObject(i).get("rating").toString()));
                        arrayOfReviews.add(review);
                    }

                    String isOpen = "";

                    if(resultObj.has("opening_hours")) {
                        if (resultObj.getJSONObject("opening_hours").get("open_now").toString().equals("true")) {
                            isOpen = "Open Now";
                        } else {
                            isOpen = "Closed";
                        }
                    } else {
                        isOpen = "Unavaliable";
                    }

                    place = new Place(resultObj.getString("place_id").toString(), resultObj.getString("name").toString(), resultObj.getString("formatted_address").toString(), location, arrayOfReviews, isOpen);



                    TextView tvTitle = (TextView) findViewById(R.id.mainTitle);



                    tvTitle.setText(place.getName());


                    LatLng attraction = new LatLng(place.getLocation().getLatitude(), place.getLocation().getLongitude());
                    mMap.addMarker(new MarkerOptions().position(attraction).title(place.getName()));
                    float zoomLevel = (float) 16.0; //This goes up to 21
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(attraction, zoomLevel));

                    adapter.addAll(arrayOfReviews);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error grabbing nearby places", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton bookmarkBtn = (ImageButton) findViewById(R.id.bookmark);
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(getApplicationContext());
                Bookmark bookmark = new Bookmark(place.getPlaceId());
                db.addBookmark(bookmark);
                db.close();
                Toast.makeText(getApplicationContext(), "Bookmarked", Toast.LENGTH_LONG);
            }
        });

        adapter = new ReviewsAdapter(this, arrayOfReviews);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewReviews);
        listView.setAdapter(adapter);


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
    }
}
