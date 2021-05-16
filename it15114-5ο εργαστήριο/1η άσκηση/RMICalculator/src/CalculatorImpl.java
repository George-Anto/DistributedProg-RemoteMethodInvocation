import java.rmi.*;
import java.rmi.server.*;

//Υλοποίηση των μεθόδων που προσφέρονται για απομακρυσμένη κλήση στο interface Calculator
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
	
	private static final long serialVersionUID = 1;

	public CalculatorImpl() throws RemoteException {
	}
	
	public int add(int number1, int number2) throws RemoteException {
		return (number1 + number2);
	}
	
	public int subtract(int number1, int number2) throws RemoteException {
		return (number1 - number2);
	}
	
	public int multiply(int number1, int number2) throws RemoteException {
		return (number1 * number2);
	}
	
	public double divide(double number1, double number2) throws RemoteException {
		if(number2 != 0)
			return (number1 / number2);
		return 0;
	}
	
	public double power(double number1, double number2) throws RemoteException {
		return (Math.pow(number1, number2));
	}
	
	public double ln(double number) throws RemoteException {
		return Math.log(number);
	}
	
	public double sq(double number) {
		return Math.sqrt(number);
	}
	
	public double reverse(double number) {
		if(number != 0)
			return (1 / number);
		else
			return 0;
	}
}
