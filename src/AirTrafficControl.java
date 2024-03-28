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

    public void requestPermissionToLand(Plane plane) throws InterruptedException {
        if (plane.isEmergency()) {
            System.out.println("Plane-" + plane.getId() + ": URGENT! Mechanical Malfunction. Request for landing");
            if (runway.tryAcquire()) {
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            } else {
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Please join circle queue & wait for instruction");
            }
            this.handleEmergencyLanding(plane);
        } else {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + plane.getId() + ": Request for landing");
            if (runway.tryAcquire()) {
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Can use the runway");
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Using runway");
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Leaving runway");
            } else {
                System.out.println("ATC: Plane-" + plane.getId() + "  -  Please join circle queue & wait for instruction");
            }
            this.handleNormalLanding(plane);
        }
    }

    private void handleNormalLanding(Plane plane) throws InterruptedException {
        runway.release();
        int gateIndex = selectDockGate(plane);
        if (gateIndex > 0) {
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex + 1);
            dockGates[gateIndex].acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex + 1);
            Thread.sleep(1000);
            plane.boardingDisembarkPassenger();
        }
    }

    private void handleEmergencyLanding(Plane plane) throws InterruptedException {
        runway.release();
        int gateIndex = selectDockGate(plane);
        if (gateIndex == 0) {
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Please dock at Gate " + gateIndex + 1);
            dockGates[gateIndex].acquire();
            System.out.println("ATC: Plane-" + plane.getId() + "  -  Dock Successfully at Gate " + gateIndex + 1);
            Thread.sleep(1000);
            plane.boardingDisembarkPassenger();
        }
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