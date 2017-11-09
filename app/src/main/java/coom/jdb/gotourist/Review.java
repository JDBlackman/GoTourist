package coom.jdb.gotourist;

/**
 * Created by Denn1s on 22/05/2017.
        */

public class Review {
    private String author;
    private String profilePicture;
    private String when;
    private String text;
    private double rating;

    public Review(String author, String profilePicture, String when, String text, double rating){
        setAuthor(author);
        setProfilePicture(profilePicture);
        setWhen(when);
        setText(text);
        setRating(rating);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
