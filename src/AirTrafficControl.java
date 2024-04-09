import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class AirTrafficControl {
    private final Semaphore runway;
    private final int numberOfGates;
    private Semaphore dockGatesNormal;
    private Semaphore dockGatesEmergency;
    protected Queue<Plane> landingQueueNormal;
    protected Queue<Plane> landingQueueEmergency;

    public AirTrafficControl() {
        runway = new Semaphore(1);
        numberOfGates = 3;
        dockGatesNormal = new Semaphore(numberOfGates - 1, true);
        dockGatesEmergency = new Semaphore(1);
        landingQueueNormal = new LinkedList<>();
        landingQueueEmergency = new LinkedList<>();
    }

    public void allowPlaneToLand(Plane plane, int gateIndex) throws InterruptedException {
        if (plane.isEmergency()) {
            dockGatesEmergency.acquire(1);
        } else {
            dockGatesNormal.acquire(1);
        }

        synchronized (runway) {
            System.out.println("[" + common.getDate() + "]" + " ATC: Plane-" + plane.getId() + "  -  Can use the runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex);
            this.useRunway();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            this.releaseRunway();
        }
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex);

        plane.disembarkingPassenger();
        plane.boardingPassenger();
        plane.luggageLoading();
        plane.cleaningAircraft();
        plane.refuelingMaintenance();

        if (plane.isEmergency()) {
            dockGatesEmergency.release(1);
        } else {
            dockGatesNormal.release(1);
        }

        synchronized (runway) {
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving gate " + gateIndex);
            this.useRunway();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            this.releaseRunway();
            plane.takeOff();
        }

        if (!landingQueueEmergency.isEmpty()) {
            Plane nextPlane = landingQueueEmergency.poll();
            int nextGateIndex = selectDockGate(nextPlane.isEmergency());
            try {
                allowPlaneToLand(nextPlane, nextGateIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (!landingQueueNormal.isEmpty()) {
            Plane nextPlane = landingQueueNormal.poll();
            int nextGateIndex = selectDockGate(nextPlane.isEmergency());
            try {
                allowPlaneToLand(nextPlane, nextGateIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public int requestLandingPermission(Plane plane) throws InterruptedException {
        boolean isEmergency = plane.isEmergency();

        if (isEmergency && isEmergencyGateAvailable()) {
            return selectDockGate(isEmergency);
        } else if (!isEmergency && isNormalGateAvailable()) {
            return selectDockGate(isEmergency);
        }

        return -1;
    }

    public boolean isNormalGateAvailable() {
        return dockGatesNormal.availablePermits() > 0;
    }

    public boolean isEmergencyGateAvailable() {
        return dockGatesEmergency.availablePermits() > 0;
    }

    private int selectDockGate(boolean isEmergency) {
        if (isEmergency) {
            return 0;
        } else {
            // acquire gate 1 and 2
            return (int) (Math.random() * (numberOfGates - 1)) + 1;
        }
    }

    synchronized public void releaseRunway() throws InterruptedException {
        runway.release();
    }

    synchronized public void useRunway() throws InterruptedException {
        runway.acquire();
    }

    public void getReport(ArrayList<Plane> planeList) throws InterruptedException {
        this.getAirportStatus();
        this.getStatistic(planeList);
    }

    public void getAirportStatus() throws InterruptedException {
        System.out.println("-----------------------------------------------");
        System.out.println("\t\t Airport Status");
        System.out.println("-----------------------------------------------");
        if (runway.availablePermits() > 0) {
            System.out.println("Runway - Clear");
        }
        if (dockGatesEmergency.availablePermits() > 0 && dockGatesNormal.availablePermits() > 1) {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Gate " + i + " - Clear");
            }
        } else {
            System.out.println("Gates are not Clear");
        }
    }

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
    }
}