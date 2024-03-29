import java.util.ArrayList;

public class Plane implements Runnable {
    private static final int MAX_CAPACITY = 50;
    private final int id;
    private final boolean emergency;
    private final AirTrafficControl atc;
    private int gateIndex;

    public Plane(int id, boolean emergency, AirTrafficControl atc) {
        this.id = id;
        this.emergency = emergency;
        this.atc = atc;
        this.gateIndex = -1;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEmergency() {
        return this.emergency;
    }

    public int getGateIndex() {
        return this.gateIndex;
    }

    private void requestForLanding() throws InterruptedException {
        ArrayList<Object> landingPermission = atc.landingPermission(this);

        if (this.emergency) {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": URGENT! Mechanical Malfunction. Request for landing");

            // Check if the plane is allowed to land
            if ((boolean) landingPermission.get(0)) {
                int gateIndex = (int) landingPermission.get(1);
                this.gateIndex = gateIndex;
                atc.handleLanding(this);
                // System.out.println("ATC: Plane-" + this.id + "  -  Can use the runway");
                // System.out.println("ATC: Plane-" + this.id + "  -  Please dock at Gate " + gateIndex + 1);

            }

        } else {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": Request for landing");
            atc.handleNormalLanding(this);
        }
    }

    public void boardingPassenger() {
        for (int i = 1; i <= MAX_CAPACITY; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " boarding...");
        }
    }

    public void disembarkingPassenger() {
        for (int i = 1; i <= MAX_CAPACITY; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " disembarking...");
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                this.requestForLanding();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
