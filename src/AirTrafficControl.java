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

        System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex);
        this.useRunway();
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
        this.releaseRunway();
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex);

        plane.disembarkingPassenger();
        plane.boardingPassenger();

        if (plane.isEmergency()) {
            dockGatesEmergency.release(1);
        } else {
            dockGatesNormal.release(1);
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

    synchronized public ArrayList<Object> requestLandingPermission1(Plane plane) throws InterruptedException {
        boolean isEmergency = plane.isEmergency();

        if (!isNormalGateAvailable() || !isEmergencyGateAvailable()) {
            handleLandingPermission(false, -1);
        }

        if (isEmergency && isEmergencyGateAvailable()) {
            return handleLandingPermission(true, 0);
        } else if (!isEmergency && isNormalGateAvailable()) {
            return handleLandingPermission(false, selectDockGate(false));
        } else {
            return handleLandingPermission(false, -1);
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

    private ArrayList<Object> handleLandingPermission(boolean permission, int gateIndex) {
        ArrayList<Object> result = new ArrayList<>();
        result.add(permission);
        result.add(gateIndex);
        return result;
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

    synchronized public void releaseDockGate() throws InterruptedException {
    }

    synchronized public void releaseRunway() throws InterruptedException {
        runway.release();
    }

    synchronized public void useRunway() throws InterruptedException {
        runway.acquire();
    }
}