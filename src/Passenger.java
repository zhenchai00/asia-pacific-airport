public class Passenger implements Runnable {
    private int passengerID;
    private int planeID;
    private boolean boarding = false;

    public Passenger(int passengerID, int planeID) {
        this.passengerID = passengerID;
        this.planeID = planeID;
    }

    @Override
    public void run() {
        // TODO: ALERT
        // once park at gate only start disembark passenger
        // wait until cleaned the aircraft only boarding passenger
        // try {
        //     disembarkPlane();
        //     Thread.sleep(100);
        //     boardPlane();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        if (boarding) {
            boardPlane();
        } else {
            disembarkPlane();
        }
    }

    private void boardPlane() {
        System.out.println("\tPlane-" + planeID + " : Passenger " + passengerID + " boarding...");
        // System.out.println("Plane-" + planeID + " : Passenger " + passengerID + " start boarding...");
        // // Simulate boarding time
        // try {
        //     Thread.sleep(1000); // Adjust boarding time as needed
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // System.out.println("Plane-" + planeID + " : Passenger " + passengerID + " finished boarding...");
    }
    
    private void disembarkPlane() {
        System.out.println("\tPlane-" + planeID + " : Passenger " + passengerID + " disembarking...");
        // System.out.println("Plane-" + planeID + " : Passenger " + passengerID + " start disembarking...");
        // // Simulate disembarking time
        // try {
        //     Thread.sleep(1000); // Adjust disembarking time as needed
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // System.out.println("Plane-" + planeID + " : Passenger " + passengerID + " finished disembarking...");
    }

    public void setBoardingStatus (boolean status) {
        this.boarding = status;
    }

    public boolean getBoardingStatus () {
        return this.boarding;
    }
}
