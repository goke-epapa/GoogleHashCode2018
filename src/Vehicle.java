import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    public int id;
    public Intersection position;
    public List<Ride> completedRides;

    public Vehicle(int id) {
        this.id = id + 1;
        this.position = new Intersection(0,0);
        completedRides = new ArrayList<>();
    }

    void completeRide(Ride ride) {
        position = ride.finish;
    }

    int distanceTo(Ride ride) {

        return Math.abs(position.row - ride.start.row) + Math.abs(position.col - ride.start.col);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", position=" + position +
                '}';
    }
}
