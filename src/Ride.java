public class Ride {

    int id;
    boolean completed;
    public Intersection start;
    public Intersection finish;

    public int earlierStart;
    public int latestFinish;

    public Ride() {
    }

    public Ride(Intersection start, Intersection finish, int earlierStart, int latestFinish) {
        this.start = start;
        this.finish = finish;
        this.earlierStart = earlierStart;
        this.latestFinish = latestFinish;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "start=" + start +
                ", finish=" + finish +
                ", earlierStart=" + earlierStart +
                ", latestFinish=" + latestFinish +
                '}';
    }

    int distanceOfRide() {

        return Math.abs(start.row - finish.row) + Math.abs(finish.col - start.col);
    }
}
