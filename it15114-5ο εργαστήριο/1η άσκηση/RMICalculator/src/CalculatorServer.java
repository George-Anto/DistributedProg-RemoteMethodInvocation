import java.rmi.server.*;
import java.rmi.registry.*;

public class CalculatorServer {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) throws Exception {
				
		System.setProperty("java.rmi.server.hostname", HOST);
                
		//Δημιουργία του απομακρυσμένου αντικειμένου που χρειάζεται για την κλήση
		//των απομακρυσμένων μεθόδων
		Calculator robj = new CalculatorImpl();
		
		//Δημιουργία του Registry
		Registry registry = LocateRegistry.createRegistry(PORT);
		
		//Δέσμευση του απομακρυσμένου αντικειμένου με ένα όνομα και δημοσιοποίηση του στο Registry
		String rmiObjectName = "Calculator";
		registry.rebind(rmiObjectName, robj);
		System.out.println("Remote object bounded.");
		
		//Τερματισμός του server με χρήση του enter
		System.out.println("Press <Enter> for exit.");
		System.in.read();
		
		//Καθαρισμός του Registry
		UnicastRemoteObject.unexportObject(robj, true);
		registry.unbind(rmiObjectName);
		System.out.println("Remote object unbounded.");
	}
}