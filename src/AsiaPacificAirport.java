// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;

public class AsiaPacificAirport {
    public static void main(String[] args) {
        AirTrafficControl airControl = new AirTrafficControl();
        // ExecutorService executor = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Airplane plane = new Airplane(i + 1);
            airControl.requestToLand(plane);
        }

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
