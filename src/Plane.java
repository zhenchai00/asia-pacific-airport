public class Plane implements Runnable {
    public AirTrafficControl atc;
    private static final int MAX_CAPACITY = 50;
    private int id;
    private boolean emergency;

    public Plane(int id, boolean emergency, AirTrafficControl atc) {
        this.id = id;
        this.emergency = emergency;
        this.atc = atc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEmergency() {
        return this.emergency;
    }

    public void boardingDisembarkPassenger() {
        for (int i = 1; i <= MAX_CAPACITY; i++) {
            // Passenger passenger = new Passenger(i, this.id);
            // passenger.run();
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " disembarking...");
        }
    }

    @Override
    public void run() {
        try {
            atc.requestPermissionToLand(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
