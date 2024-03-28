public class Plane implements Runnable {
    private static final int MAX_CAPACITY = 50;
    private final int id;
    private final boolean emergency;
    private final AirTrafficControl atc;

    public Plane(int id, boolean emergency, AirTrafficControl atc) {
        this.id = id;
        this.emergency = emergency;
        this.atc = atc;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEmergency() {
        return this.emergency;
    }

    private void requestForLanding() throws InterruptedException {
        if (this.emergency) {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": URGENT! Mechanical Malfunction. Request for landing");
            atc.handleEmergencyLanding(this);
        } else {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": Request for landing");
            atc.handleNormalLanding(this);
        }
    }

    public void boardingDisembarkPassenger() {
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
