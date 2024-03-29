import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AirTrafficControl {
    private final Semaphore runway;
    private Semaphore[] dockGates;
    private ArrayList<Object> landingPermission;

    public AirTrafficControl() {
        runway = new Semaphore(1);
        dockGates = new Semaphore[3];
        for (int i = 0; i < 3; i++) {
            this.dockGates[i] = new Semaphore(1);
        }
        landingPermission = new ArrayList<>();
    }

    public void handleNormalLanding(Plane plane) throws InterruptedException {
        this.handleLanding(plane);
    }

    public void handleEmergencyLanding(Plane plane) throws InterruptedException {
        this.handleLanding(plane);
    }

    // private void handleLanding(Plane plane) throws InterruptedException {
    //     boolean isGateAvailable = isGateAvailable();
    //     while (!isGateAvailable) {
    //         wait();
    //     }
    //     int gateIndex = selectDockGate(plane);
    //     if (gateIndex >= 0) {
    //         System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
    //         runway.acquire();
    //         System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex + 1);
    //         dockGates[gateIndex].acquire();
    //         System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
    //         System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
    //         System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex + 1);
    //         runway.release();
    //         releaseDockGate(gateIndex);
    //     }
    //     notify();
    // }

    public void handleLanding(Plane plane) throws InterruptedException {
        int gateIndex = plane.getGateIndex();
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex + 1);
        this.useRunway();
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
        this.releaseRunway();
        System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex + 1);
    }

    synchronized public ArrayList<Object> landingPermission(Plane plane) throws InterruptedException {
        if (!isGateAvailable()) {
            wait();
        }
        landingPermission.add(isGateAvailable());
        landingPermission.add(selectDockGate(plane));
        notify();
        return landingPermission;
    }

    public boolean isGateAvailable() {
        for (Semaphore gate : dockGates) {
            // System.out.println("Gate available permits: " + gate.availablePermits());
            if (gate.availablePermits() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Select a dock gate for the plane to dock and acquire the gate
     * @param plane
     * @return the index of the dock gate selected, -1 if no gate is available
     */
    private int selectDockGate(Plane plane) {
        if (plane.isEmergency()) {
            return 0;
        } else {
            // acquire gate 1 and 2 
            for (int i = 1; i < dockGates.length; i++) {
                if (dockGates[i].tryAcquire()) {
                    return i;
                }
            }
            return -1;
        }
    }

    public void releaseDockGate(int gateIndex) {
        dockGates[gateIndex].release();
    }

    public void releaseRunway() {
        runway.release();
    }

    public void useRunway() throws InterruptedException {
        runway.acquire();
    }
}