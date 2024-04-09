import java.util.*;

public class AsiaPacificAirport {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\t\t Welcome to Asia Pacific Airport");
        System.out.println("-----------------------------------------------------------------------");
        AirTrafficControl atc = new AirTrafficControl();
        List<Thread> planeThreads = new ArrayList<>();
        ArrayList<Plane> planeList = new ArrayList<>();
        int planeCount = 6;

        try {

            Random rand = new Random();
            HashSet<Integer> emergencyPlanes = new HashSet<>();

            // random generate the emergency plane
            while (emergencyPlanes.size() < 2) {
            int planeId = rand.nextInt(planeCount) + 1;
            emergencyPlanes.add(planeId);
            }

            for (int i = 1; i <= planeCount; i++) {
                try {
                    Thread.sleep(rand.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean isEmergency = emergencyPlanes.contains(i);
                // boolean isEmergency = false;
                // if (i == 3 || i == 6) {
                //     isEmergency = true;
                // }
                Plane plane = new Plane(i, isEmergency, atc);
                Thread planeThread = new Thread(plane);
                planeThreads.add(planeThread);
                planeThread.start();
                planeList.add(plane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                for (Thread planeThread : planeThreads) {
                    try {
                        planeThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                atc.getReport(planeList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
