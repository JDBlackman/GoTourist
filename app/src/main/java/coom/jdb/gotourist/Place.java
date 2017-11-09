package coom.jdb.gotourist;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Denn1s on 21/05/2017.
 */

public class Place {
    private String id;
    private String name;
    private String address;
    private String shortDesc;
    private String imageUrl;
    private String isOpen;
    private String placeId;

    private Location location;

    private ArrayList<Review> reviews;

    private double rating;
    private double distance;

    public Place(String Id, String name, double rating, String imageUrl, double distance, String isOpen){
        setId(Id);
        setName(name);
        setRating(rating);
        setImageUrl(imageUrl);
        setDistance(distance);
        setOpen(isOpen);
    }

    public Place(String placeId, String name, String address, Location location, ArrayList<Review> reviews, String isOpen){
        setName(name);
        setAddress(address);
        setLocation(location);
        setReviews(reviews);
        setPlaceId(placeId);
        setOpen(isOpen);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getOpen() {
        return getIsOpen();
    }

    public void setOpen(String open) {
        setIsOpen(open);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
