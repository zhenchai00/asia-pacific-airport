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
        // ArrayList<Object> landingPermission = atc.requestLandingPermission(this);
        // boolean isAllowedToLand = (boolean) landingPermission.get(0);
        // int gateNum = (int) landingPermission.get(1);

        int gateNum = atc.requestLandingPermission(this);
        System.out.println("gat num = " + gateNum + " emergen " + this.emergency);
        if (this.emergency) {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": URGENT! Mechanical Malfunction. Request for landing");
            if (gateNum < 0) {
                atc.landingQueueEmergency.add(this);
            } else {
                atc.allowPlaneToLand(this, gateNum + 1);
            }

        } else {
            System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": Request for landing");
            if (gateNum < 0) {
                atc.landingQueueNormal.add(this);
            } else {
                atc.allowPlaneToLand(this, gateNum + 1);
            }
        }


        // if (gateNum < 0) {
        //     if (this.emergency) {
        //         atc.landingQueueEmergency.add(this);
        //     } else {
        //         atc.landingQueueNormal.add(this);
        //     }
        // } else {
        //     if (this.emergency) {
        //         System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": URGENT! Mechanical Malfunction. Request for landing");
        //             System.out.println("Plane number - " + this.id + " gate - " + gateNum);
        //             atc.allowPlaneToLand(this, gateIndex);
        //     } else {
        //         System.out.println("[" + common.getDate() + "]" + " Plane-" + this.id + ": Request for landing");
        //             System.out.println("Plane number - " + this.id + " gate - " + gateNum);
        //             atc.allowPlaneToLand(this, gateIndex);
        //     }
        // }
    }

    public void boardingPassenger() throws InterruptedException {
        // for (int i = 1; i <= MAX_CAPACITY; i++) {
        for (int i = 1; i <=3; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " boarding...");
            Thread.sleep(100);
        }
    }

    public void disembarkingPassenger() throws InterruptedException {
        // for (int i = 1; i <= MAX_CAPACITY; i++) {
        for (int i = 1; i <= 3; i++) {
            System.out.println("\tPlane-" + this.id + " : Passenger " + i + " disembarking...");
            Thread.sleep(100);
        }
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
