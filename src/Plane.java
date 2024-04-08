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
        int gateNum = atc.requestLandingPermission(this);
        if (this.emergency) {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": URGENT! Mechanical Malfunction. Request for landing");
            if (gateNum < 0) {
                atc.landingQueueEmergency.add(this);
                System.out.println("ATC: Plane-" + this.id + " Please join circle queue & wait for instruction.");
            } else {
                atc.allowPlaneToLand(this, gateNum + 1);
            }

        } else {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": Request for landing");
            if (gateNum < 0) {
                atc.landingQueueNormal.add(this);
                System.out.println("ATC: Plane-" + this.id + " Please join circle queue & wait for instruction.");
            } else {
                atc.allowPlaneToLand(this, gateNum + 1);
            }
        }
    }

    public void boardingPassenger() throws InterruptedException {
        // for (int i = 1; i <= MAX_CAPACITY; i++) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " boarding...");
            Thread.sleep(100);
        }
    }

    public void disembarkingPassenger() throws InterruptedException {
        // for (int i = 1; i <= MAX_CAPACITY; i++) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " disembarking...");
            Thread.sleep(100);
        }
    }

    public void luggageLoading() throws InterruptedException {
        System.out.println("\tPlane-" + this.id + " : Unloading luggage");
        Thread.sleep(1000);
        System.out.println("\tPlane-" + this.id + " : Unloading luggage - Finished");
    }

    public void cleaningAircraft() throws InterruptedException {
        System.out.println("\tPlane-" + this.id + " : Cleaning & restocking catering supplies");
        Thread.sleep(1000);
        System.out.println("\tPlane-" + this.id + " : Clean & restock catering supplies - Finished");
    }

    public void refuelingMaintenance() throws InterruptedException {
        System.out.println("\tPlane-" + this.id + " : Refueling and doing maintenance");
        Thread.sleep(1000);
        System.out.println("\tPlane-" + this.id + " : Refueling and doing maintenance - Finished");
    }

    @Override
    public void run() {
        try {
            this.requestForLanding();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
