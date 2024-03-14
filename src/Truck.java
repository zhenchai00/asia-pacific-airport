public class Truck implements Runnable{
    private Thread thread;
	
    Truck() {
        System.out.println("Truck Created");
    }

    public void run () {
        System.out.println("Running Truck");
        try {
            fuellingAircraft("temp");
        } catch (Exception e) {
            System.out.println("Thread Truck interrupted");
        }
    }

    public void start () {
        System.out.println("Starting Truck");
        if (thread == null) {
            thread = new Thread(this, "Truck");
            thread.start();
        }
    }

    public void fuellingAircraft (String aircraftName) {
        try {
            System.out.println(aircraftName + " \t: start fueling");
            this.thread.sleep(1000);
            System.out.println(aircraftName + " \t: finish fueling");
        } catch (Exception e) {
        }
    }
}
