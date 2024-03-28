import java.util.concurrent.Semaphore;

public class AirTrafficControl {
    private final Semaphore runway;
    private Semaphore[] dockGates;

    public AirTrafficControl() {
        runway = new Semaphore(1);
        dockGates = new Semaphore[3];
        for (int i = 0; i < 3; i++) {
            this.dockGates[i] = new Semaphore(1);
        }
    }

    public void handleNormalLanding(Plane plane) throws InterruptedException {
        this.handleLanding(plane);
    }

    public void handleEmergencyLanding(Plane plane) throws InterruptedException {
        this.handleLanding(plane);
    }

    private void handleLanding(Plane plane) throws InterruptedException {
        boolean isGateAvailable = checkAvailableGate();
        System.out.println("Plane- " + plane.getId() + " Gate available: " + isGateAvailable);
        while (!isGateAvailable) {
            wait();
        }
        int gateIndex = selectDockGate(plane);
        if (gateIndex >= 0) {
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
            runway.acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex + 1);
            dockGates[gateIndex].acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex + 1);
            runway.release();
            releaseDockGate(gateIndex);
        }
        notify();
    }

    public boolean checkAvailableGate() {
        for (int i = 0; i < dockGates.length; i++) {
            System.out.println("Gate " + i + " available: " + dockGates[i].availablePermits());
            if (dockGates[i].availablePermits() > 0) {
                return true;
            }
        }
        return false;
    }

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
}