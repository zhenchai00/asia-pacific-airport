public class Airplane implements Runnable {
    private String threadName;
    private Thread thread;

    public Airplane (String threadName) {
        this.threadName = threadName;
    }

    public void run () {
        System.out.println(threadName + " running");
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
            System.out.println(threadName + " interrupted");
        }
    }

    public void start () {
        System.out.println(threadName + " \t: started");
        if (thread == null) {
            thread = new Thread(this, "");
            thread.start();
        }
    }
    
    public void disembarkPassenger () {
        try {
            System.out.println(threadName + " \t: disembark passenger");
            for (int i = 1; i <= 50; i++) {
                System.out.println(threadName + " \t: disembarking passenger-" + i);
            }
        } catch (Exception e) {
        }
    }

    public void embarkPassenger () {
        try {
            System.out.println(threadName + " \t: Embark passenger");
            for (int i = 1; i <= 50; i++) {
                System.out.println(threadName + " \t: embarking passenger-" + i);
            }
        } catch (Exception e) {
        }
    }

    public void refillSupplies() {
        try {
            System.out.println(threadName + " \t: start refilling supplies");
            this.thread.sleep(1000);
            System.out.println(threadName + " \t: finish refilling supplies");
        } catch (Exception e) {
        }
    }

    public void cleanAircraft() {
        try {
            System.out.println(threadName + " \t: cleaning aircraft");
            this.thread.sleep(1000);
            System.out.println(threadName + " \t: finish cleaning aircraft");
        } catch (Exception e) {
        }
    }
}