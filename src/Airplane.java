public class Airplane implements Runnable {
    private int id;
    private Thread thread;
    private AirTrafficControl airTrafficControl;

    public Airplane (int id) {
        this.id = id;
    }

    public Airplane (int id, AirTrafficControl airTrafficControl) {
        this.id = id;
        this.airTrafficControl = airTrafficControl;
    }

    // @Override
    // public void run () {
    //     airTrafficControl.requestToLand(this);
    // }

    public void run () {
        System.out.println(id + " running");
        try {
            for (int i = 0; i <= 4; i++) {
                switch (i) {
                    case 1:
                        disembarkPassenger();
                        break;
                    case 2:
                        refillSupplies();
                        break;
                    case 3:
                        cleanAircraft();
                        break;
                    case 4:
                        embarkPassenger();
                        break;
                
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(id + " interrupted");
        }
    }

    public void start () {
        System.out.println("\tPlane " + id + " : started");
        if (thread == null) {
            thread = new Thread(this, "");
            thread.start();
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public void disembarkPassenger () {
        try {
            System.out.println("\tPlane " + id + " : DISEMBARK PASSENGER");
            for (int i = 1; i <= 50; i++) {
                System.out.println("\tPlane " + id + " : DISEMBARKING PASSENGER-" + i);
            }
        } catch (Exception e) {
        }
    }

    public void embarkPassenger () {
        try {
            System.out.println("\tPlane " + id + " : Embark passenger");
            for (int i = 1; i <= 50; i++) {
                System.out.println("\tPlane " + id + " : embarking passenger-" + i);
            }
        } catch (Exception e) {
        }
    }

    public void refillSupplies() {
        try {
            System.out.println("\tPlane " + id + " : start refilling supplies");
            // this.thread.sleep(1000);
            System.out.println("\tPlane " + id + " : finish refilling supplies");
        } catch (Exception e) {
        }
    }

    public void cleanAircraft() {
        try {
            System.out.println("\tPlane " + id + " : cleaning aircraft");
            // this.thread.sleep(1000);
            System.out.println("\tPlane " + id + " : finish cleaning aircraft");
        } catch (Exception e) {
        }
    }
}