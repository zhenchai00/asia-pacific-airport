public class AsiaPacificAirport {
	public static void main(String[] args) {
		AirTrafficControl airControl = new AirTrafficControl();

		Airplane a1 = new Airplane("Plane-1");
		Airplane a2 = new Airplane("Plane-2");
		Airplane a3 = new Airplane("Plane-3");
		Airplane a4 = new Airplane("Plane-4");
		Airplane a5 = new Airplane("Plane-5");
		Airplane a6 = new Airplane("Plane-6");

		a1.start();
		a2.start();
		a3.start();
		a4.start();
		a5.start();
		a6.start();
	}
}
