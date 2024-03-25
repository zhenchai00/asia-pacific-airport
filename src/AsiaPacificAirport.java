// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;

public class AsiaPacificAirport {
    public static void main(String[] args) {
        AirTrafficControl airControl = new AirTrafficControl();
        // ExecutorService executor = Executors.newFixedThreadPool(6);

        // for (int i = 0; i < 6; i++) {
        //     // try {
        //     //     Thread.sleep(1000);
        //     // } catch (Exception e) {
        //     //     e.printStackTrace();
        //     // }
        //     Airplane plane = new Airplane(i + 1);
        //     airControl.requestToLand(plane);
        //     System.out.println("Plane " + plane.getId());
        // }

        for (int i = 0; i < 6; i++) {
            try {
                Airplane plane = new Airplane(i + 1, airControl);
                plane.start();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Airplane plane1 = new Airplane(1, airControl);
        // Airplane plane2 = new Airplane(2, airControl);
        // Airplane plane3 = new Airplane(3, airControl);
        // Airplane plane4 = new Airplane(4, airControl);
        // Airplane plane5 = new Airplane(5, airControl);
        // Airplane plane6 = new Airplane(6, airControl);

        // plane1.setPriority(Thread.MAX_PRIORITY);
        // plane2.setPriority(Thread.MAX_PRIORITY);
        // plane3.setPriority(Thread.NORM_PRIORITY);
        // plane4.setPriority(Thread.NORM_PRIORITY);
        // plane5.setPriority(Thread.MIN_PRIORITY);
        // plane6.setPriority(Thread.MIN_PRIORITY);

        // plane1.start();
        // plane2.start();
        // plane3.start();
        // plane4.start();
        // plane5.start();
        // plane6.start();

        // airControl.requestToLand(plane1);
        // airControl.requestToLand(plane2);
        // airControl.requestToLand(plane3);
        // airControl.requestToLand(plane4);
        // airControl.requestToLand(plane5);
        // airControl.requestToLand(plane6);

        // for (int i = 0; i < 6; i++) {
        //     Airplane plane = new Airplane(i + 1, airControl);
        //     executor.submit(plane);
        //     try {
        //         Thread.sleep(000);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
        // executor.shutdown();
    }
}
