package coom.jdb.gotourist;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BookmarksActivity extends AppCompatActivity {

    private static ArrayList<Place> arrayOfBookmarks = new ArrayList<Place>();
    private static PlacesAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter to convert the array to views
        adapter = new PlacesAdapter(this, arrayOfBookmarks);
        ListView listView = (ListView) findViewById(R.id.listViewBookmarks);
        listView.setAdapter(adapter);

        DBHandler db = new DBHandler(this);

        ArrayList<Bookmark> arrayOfBookmarksRaw = db.getAllBookmarks();
        for (Bookmark bookmark : arrayOfBookmarksRaw) {
            Log.d("URL", "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + bookmark.getPlaceId() + "&key=AIzaSyAFKztwuiyP2noQlGXkRpDohUejTLLb4Kk");
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + bookmark.getPlaceId() + "&key=AIzaSyAFKztwuiyP2noQlGXkRpDohUejTLLb4Kk", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONObject resultObj = response.getJSONObject("result");

                        ArrayList<Review> arrayOfReviews = new ArrayList<Review>();

                        Location location = new Location("");
                        location.setLatitude(Double.parseDouble(resultObj.getJSONObject("geometry").getJSONObject("location").getString("lat")));
                        location.setLongitude(Double.parseDouble(resultObj.getJSONObject("geometry").getJSONObject("location").getString("lng")));

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

                        Place place = new Place(resultObj.getString("place_id").toString(), resultObj.getString("name").toString(), resultObj.getString("formatted_address").toString(), location, arrayOfReviews, isOpen);
                        adapter.add(place);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Error grabbing nearby places", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
    }
}
