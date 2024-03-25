import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AirTrafficControl {
    private BlockingQueue<Airplane> runway;
    private DockGate[] gates;

    public AirTrafficControl () {
        // runway only can fit one plane in one time
        this.runway = new ArrayBlockingQueue<>(1);

        // three plane can park at the gate in the same time
        this.gates = new DockGate[3];

        for (int i = 0; i < 3; i++) {
            this.gates[i] = new DockGate(i + 1);
        }
    }

    public void requestToLand (Airplane plane) {
        try {
            System.out.println("[" + common.getDate() + "]" + " Plane " + plane.getId() + " requesting permission to land!");
            runway.put(plane);
            plane.disembarkPassenger();
            plane.cleanAircraft();
            plane.refillSupplies();
            plane.embarkPassenger();
            System.out.println("ATC: Plane " + plane.getId() + " has landed and is waiting for gate assignment.");
            assignGate(plane);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void assignGate (Airplane plane) {
        DockGate availableGate = null;
        for (DockGate gate : gates) {
            if (gate.acquire()) {
                availableGate = gate;
                break;
            }
        }

        if (availableGate != null) {
            System.out.println("ATC: Plane " + plane.getId() + " has been assigned to Gate " + availableGate.getId());
            availableGate.release();
            runway.remove(plane);
            System.out.println("ATC: Plane " + plane.getId() + " has left the gate and taken off.");
        }
    }
}
