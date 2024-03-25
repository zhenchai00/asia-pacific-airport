// public class Airplane implements Runnable {
public class Airplane extends Thread {
    private int id;
    private Thread thread;
    private final AirTrafficControl airTrafficControl;

    // public Airplane (int id) {
    //     this.id = id;
    // }

    public Airplane (int id, AirTrafficControl airTrafficControl) {
        this.id = id;
        this.airTrafficControl = airTrafficControl;
    }

    // @Override
    // public void run () {
    //     airTrafficControl.requestToLand(this);
    // }

    @Override
    public void run () {
        System.out.println(id + " running");
        try {
            airTrafficControl.requestToLand(this);
            // for (int i = 0; i <= 6; i++) {
            //     switch (i) {
            //         case 1:
            //             disembarkPassenger();
            //             break;
            //         case 2:
            //             unLoadLuggage();
            //             break;
            //         case 3:
            //             refillSupplies();
            //             break;
            //         case 4:
            //             cleanAircraft();
            //             break;
            //         case 5:
            //             embarkPassenger();
            //             break;
            //         case 6:
            //             reLoadLuggage();
            //             break;
                
            //         default:
            //             break;
            //     }
            // }
        } catch (Exception e) {
            System.out.println(id + " interrupted");
            e.printStackTrace();
        }
    }

    public void start () {
        System.out.println("\tPlane " + id + " : STARTED");
        if (thread == null) {
            thread = new Thread(this, "");
            thread.start();
        }
    }
    
    public long getId() {
        return this.id;
    }
    
    public void disembarkPassenger () {
        try {
            System.out.println("\tPlane " + id + " : DISEMBARK PASSENGER");
            for (int i = 1; i <= 50; i++) {
                Thread.sleep(500);
                System.out.println("\tPlane " + id + " : DISEMBARKING PASSENGER-" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void embarkPassenger () {
        try {
            System.out.println("\tPlane " + id + " : EMBARK PASSENGER");
            for (int i = 1; i <= 50; i++) {
                Thread.sleep(500);
                System.out.println("\tPlane " + id + " : EMBARKING PASSENGER-" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unLoadLuggage () {
        try {
            System.out.println("\tPlane " + id + " : UNLOADING LUGGAGE");
            Thread.sleep(1000);
            System.out.println("\tPlane " + id + " : UNLOAD LUGGAGE - FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reLoadLuggage () {
        try {
            System.out.println("\tPlane " + id + " : RELOADING LUGGAGE");
            Thread.sleep(1000);
            System.out.println("\tPlane " + id + " : RELOAD LUGGAGE - FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refillSupplies() {
        try {
            System.out.println("\tPlane " + id + " : REFILLING SUPPLIES");
            Thread.sleep(1000);
            System.out.println("\tPlane " + id + " : REFILLING SUPPLIES - FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanAircraft() {
        try {
            System.out.println("\tPlane " + id + " : CLEANING AIRCRAFT");
            Thread.sleep(1000);
            System.out.println("\tPlane " + id + " : CLEANING AIRCRAFT - FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}