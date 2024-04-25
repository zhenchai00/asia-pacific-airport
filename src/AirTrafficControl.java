import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class AirTrafficControl {
    private final Semaphore runway;
    private final int numberOfGates;
    private Semaphore[] dockGates;
    protected Queue<Plane> landingQueueNormal;
    protected Queue<Plane> landingQueueEmergency;

    public AirTrafficControl() {
        runway = new Semaphore(1);
        numberOfGates = 3;
        dockGates = new Semaphore[3];
        for (int i = 0; i < numberOfGates; i ++ ) {
            dockGates[i] = new Semaphore(1);
        }
        landingQueueNormal = new LinkedList<>();
        landingQueueEmergency = new LinkedList<>();
    }

    public void allowPlaneToLand(Plane plane, int gateIndex) throws InterruptedException {
        dockGates[gateIndex].acquire(1);
        int gateNum = gateIndex + 1;

        synchronized (runway) {
            System.out.println("[" + common.getDate() + "]" + " ATC: Plane-" + plane.getId() + "  -  Can use the runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateNum);
            runway.acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            runway.release();
        }
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateNum);

        plane.disembarkingPassenger();
        plane.luggageUnLoading();
        plane.cleaningAircraft();
        plane.boardingPassenger();
        plane.luggageLoading();
        plane.refuelingMaintenance();

        dockGates[gateIndex].release(1);

        synchronized (runway) {
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving gate " + gateNum);
            runway.acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            runway.release();
            plane.takeOff();
        }

        if (!landingQueueEmergency.isEmpty()) {
            Plane nextPlane = landingQueueEmergency.poll();
            int nextGateIndex = selectDockGate();
            try {
                allowPlaneToLand(nextPlane, nextGateIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (!landingQueueNormal.isEmpty()) {
            Plane nextPlane = landingQueueNormal.poll();
            int nextGateIndex = selectDockGate();
            try {
                allowPlaneToLand(nextPlane, nextGateIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public int getAvailableDockGate() throws InterruptedException {
        return selectDockGate();
    }

    public boolean isDockGateAvailable(int gateIndex) {
        return dockGates[gateIndex].availablePermits() > 0;
    }

    private int selectDockGate() {
        for (int i = 0; i < numberOfGates; i++) {
            if (isDockGateAvailable(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method is to get the summary of the airport report
     * @param planeList
     * @throws InterruptedException
     */
    public void getReport(ArrayList<Plane> planeList) throws InterruptedException {
        this.getAirportStatus();
        this.getStatistic(planeList);
    }

    /**
     * This method is to get the Airport status, check runway and dock gates are cleared
     * @throws InterruptedException
     */
    public void getAirportStatus() throws InterruptedException {
        System.out.println("-----------------------------------------------");
        System.out.println("\t\t Airport Status");
        System.out.println("-----------------------------------------------");
        if (runway.availablePermits() > 0) {
            System.out.println("Runway - Clear");
        }
        // if (dockGatesEmergency.availablePermits() > 0 && dockGatesNormal.availablePermits() > 1) {
        for (int i = 0; i < numberOfGates; i++) {
            if (dockGates[i].availablePermits() > 0) {
                int num = i + 1;
                System.out.println("Gate " + num + " - Clear");
            } else {
                System.out.println("Gates are not Clear");
            }
        }
    }

    /**
     * This method is to calculate total disembark and boarding passenger,
     * calculate max, min, average time used by plane and total plane
     * @param planeList
     * @throws InterruptedException
     */
    public void getStatistic(ArrayList<Plane> planeList) throws InterruptedException {
        System.out.println("-----------------------------------------------");
        System.out.println("\t\t Statistic");
        System.out.println("-----------------------------------------------");
        long totalUsedTime = 0;
        int boardingPassenger = 0;
        int disembarkingPassenger = 0;
        for (Plane plane : planeList) {
            long totalTime = plane.getTotalTime();
            int totalBoardingPassenger = plane.getTotalBoardingPassenger();
            int totalDisembarkPassenger = plane.getTotalDisembarkPassenger();
            totalUsedTime += totalTime;
            boardingPassenger += totalBoardingPassenger;
            disembarkingPassenger += totalDisembarkPassenger;
        }

        // https://www.geeksforgeeks.org/finding-maximum-element-of-java-arraylist/
        long max = planeList.get(0).getTotalTime();
        for (int i = 1; i < planeList.size(); i++) {
            if (max < planeList.get(i).getTotalTime()) {
                max = planeList.get(i).getTotalTime();
            }
        }

        // https://www.geeksforgeeks.org/finding-minimum-element-of-java-arraylist/
        long min = planeList.get(0).getTotalTime();
        for (int i = 1; i < planeList.size(); i++) {
            if (min > planeList.get(i).getTotalTime()) {
                min = planeList.get(i).getTotalTime();
            }
        }

        int totalPlanes = planeList.size();
        double averageTime = (double) totalUsedTime / totalPlanes;

        System.out.println("Plane in Total: " + totalPlanes);
        System.out.println("Passenger Disembark in Total: " + disembarkingPassenger);
        System.out.println("Passenger Boarding in Total: " + boardingPassenger);
        System.out.println("Maximum Used Time: " + max + " seconds");
        System.out.println("Minimum Used Time: " + min + " seconds");
        System.out.println("Average Used Time: " + averageTime + " seconds");
        System.out.println("Total Used Time: " + totalUsedTime + " seconds");
    }
}