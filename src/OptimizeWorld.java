import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OptimizeWorld {

    public int rows;
    public int cols;
    public int totalNoOfVehicles;
    public int noOfRides;
    public List<Vehicle> fleet;
    public List<Ride> rides;
    public int bonus;
    public int noOfSteps;
    public int currentStep;

    void parse(String filename) {

        int bufferSize = 8 * 1024;

        int lineCount = 0;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename), bufferSize);
            String line = bufferedReader.readLine();

            String[] firstLine = line.split(" ");

            rows = Integer.parseInt(firstLine[0]);
            cols = Integer.parseInt(firstLine[1]);
            totalNoOfVehicles = Integer.parseInt(firstLine[2]);
            noOfRides = Integer.parseInt(firstLine[3]);
            bonus = Integer.parseInt(firstLine[4]);
            noOfSteps = Integer.parseInt(firstLine[5]);


            fleet = new ArrayList<>(totalNoOfVehicles);
            rides = new ArrayList<>();

            for (int i = 0; i < totalNoOfVehicles; i++) {
                fleet.add(new Vehicle(i));
            }

            for (int i = 0; i < noOfRides; i++) {

                String l = bufferedReader.readLine();
                String[] arr = l.split(" ");

                Ride ride = new Ride();
                ride.start = new Intersection(intValue(arr[0]), intValue(arr[1]));
                ride.finish = new Intersection(intValue(arr[2]), intValue(arr[3]));
                ride.earlierStart = intValue(arr[4]);
                ride.latestFinish = intValue(arr[5]);
                ride.id = i;

                rides.add(ride);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void simulate() {

        while (currentStep < noOfSteps) {
            boolean bonusFound = false;
            for (Ride ride : rides) {
                if (canGetBonus(ride)) {
                    bonusFound = true;
                    Vehicle closestVehicle = null;
                    int distance = Integer.MAX_VALUE;
                    for (int i = 0; i < totalNoOfVehicles; i++) {

                        Vehicle vehicle = fleet.get(i);
                        if (vehicle.distanceTo(ride) < distance) {
                            closestVehicle = vehicle;
                            distance = vehicle.distanceTo(ride);
                        }

                    }

                    closestVehicle.completeRide(ride);
                    closestVehicle.completedRides.add(ride);
                    ride.completed = true;
                    currentStep += ride.distanceOfRide();
                }

            }

            if(!bonusFound) {
                for (Ride ride : rides) {
                    if (canStartRide(ride)) {
                        Vehicle closestVehicle = null;
                        int distance = Integer.MAX_VALUE;
                        for (int i = 0; i < totalNoOfVehicles; i++) {

                            Vehicle vehicle = fleet.get(i);
                            if (vehicle.distanceTo(ride) < distance) {
                                closestVehicle = vehicle;
                                distance = vehicle.distanceTo(ride);
                            }

                        }

                        closestVehicle.completeRide(ride);
                        closestVehicle.completedRides.add(ride);
                        ride.completed = true;
                        currentStep += ride.distanceOfRide();
                    }

                }
            }
            currentStep++;
        }

    }

    void print2(String filename) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");

            for (int j = 0; j < fleet.size(); j++) {
                Vehicle vehicle = fleet.get(j);
                writer.print(vehicle.completedRides.size() + " ");

                for (int i = 0; i < vehicle.completedRides.size() - 1; i++) {
                    writer.print(vehicle.completedRides.get(i).id + " ");
                }
                if (!vehicle.completedRides.isEmpty()) {
                    writer.print(vehicle.completedRides.get(vehicle.completedRides.size() - 1).id);
                }
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void print(String filename) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            bufferedWriter = new BufferedWriter(fileWriter);

            for (int j = 0; j < fleet.size(); j++) {
                Vehicle vehicle = fleet.get(j);
                if (!vehicle.completedRides.isEmpty()) {
                    bufferedWriter.write(vehicle.id + " ");

                    for (int i = 0; i < vehicle.completedRides.size() - 1; i++) {
                        bufferedWriter.write(vehicle.completedRides.get(i).id + " ");
                    }
                    bufferedWriter.write(vehicle.completedRides.get(vehicle.completedRides.size() - 1).id);

                    if (j != fleet.size() - 1) {
                        bufferedWriter.write ("\n");
                    }
                }


            }
            fileWriter.flush();
        } catch (IOException e) {

        } finally {

            try {

                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean canStartRide(Ride ride) {

        return currentStep <= ride.earlierStart
                && !ride.completed;
    }

    boolean canGetBonus(Ride ride) {
        return currentStep == ride.earlierStart
                && !ride.completed;
    }

    static int intValue(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return "World{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", totalNoOfVehicles=" + totalNoOfVehicles +
                ", noOfRides=" + noOfRides +
                ", fleet=" + fleet +
                ", rides=" + rides +
                ", bonus=" + bonus +
                ", noOfSteps=" + noOfSteps +
                ", currentStep=" + currentStep +
                '}';
    }
}
