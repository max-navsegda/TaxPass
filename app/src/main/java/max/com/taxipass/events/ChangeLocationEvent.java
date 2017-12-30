package max.com.taxipass.events;

import android.location.Location;



public class ChangeLocationEvent {
    private Location location;

    public ChangeLocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
