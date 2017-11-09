package coom.jdb.gotourist;

/**
 * Created by Denn1s on 22/05/2017.
 */

public class Bookmark {

    private String placeId;

    public Bookmark(String placeId){
        setPlaceId(placeId);
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
