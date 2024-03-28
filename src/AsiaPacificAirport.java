import java.util.*;
import java.util.concurrent.Semaphore;

public class AsiaPacificAirport {
    public static void main(String[] args) {
        AirTrafficControl atc = new AirTrafficControl();

        Random rand = new Random();
        int planeCount = 6;
        HashSet<Integer> emergencyPlanes = new HashSet<>();

        // random generate the emergency plane
        while (emergencyPlanes.size() < 2) {
            int planeId = rand.nextInt(planeCount) + 1;
            emergencyPlanes.add(planeId);
        }

        Semaphore airportGrounds = new Semaphore(3);
        for (int i = 1; i <= planeCount; i++) {
            try {
                Thread.sleep(rand.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean isEmergency = emergencyPlanes.contains(i);
            Plane plane = new Plane(i, isEmergency, atc, airportGrounds);
            Thread planeThread = new Thread(plane);
            planeThread.start();
        }
    }
}
