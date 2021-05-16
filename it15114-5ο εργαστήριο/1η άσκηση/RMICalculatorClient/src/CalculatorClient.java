import java.rmi.*;
import java.rmi.registry.*;

public class CalculatorClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
				
		try {
			//Εύρεση του registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			//Εύρεση του απομακρυσμένου αντικειμένου
			String rmiObjectName = "Calculator";
			Calculator ref = (Calculator)registry.lookup(rmiObjectName);
			
			//Δεν κατάφερα να υλοποιήσω κάποιον τρόπο με τον οποίο ο πελάτης θα ανακαλύπτει
			//τους διαθέσιμους υπολογισμούς με την κλήση της μεθόδου registry.list()
			String[] a = registry.list();
			System.out.println(a[0]);
			
			//Κλήσεις των μεθόδων που είναι διαθέσιμες επί του απομακρυσμένου αντικειμένου
			//Ένα παράδειγμα για πιθανή κλήση κάθε απομακρυσμένης μεθόδου με διαδοχικές πράξεις 
			int result1 = ref.add(3, 5);
			System.out.println("The addition of 3 and 5 is " + result1);
			
			int result2 = ref.subtract(80, 23);
			System.out.println("The subtraction of 23 out of 80 is " + result2);
			
			double result3 = ref.ln(result2);
			System.out.println("The natural logarith of  " + result2 + " is " + result3);
			
			int result4 = ref.multiply(result1, result2);
			System.out.println("The multiplication of " + result1 + " and " + result2 + " is " + result4);
			
			double result5 = ref.power(result4, result1);
			System.out.println(result4 + "^" + result1 + " = " + result5);
			
			double result6 = ref.reverse(result2);
			System.out.println("The " + result2 + " in reverse is " + result6);
			
			double result7 = ref.sq(result2);
			System.out.println("Also " + result7 + " is the square root of " + result2);
			
			double result8 = ref.divide(result4, 10);
			System.out.println("Finally " + result4 + " divided by 10 is " + result8);
			
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}
}

