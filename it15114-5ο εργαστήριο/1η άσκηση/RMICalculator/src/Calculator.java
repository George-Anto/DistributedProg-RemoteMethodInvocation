import java.rmi.*;

public interface Calculator extends Remote {
	
	//Υπογραφές των απομακρυσμένων μεθόδων
	
	public int add(int number1, int number2) throws RemoteException;
	
	public int subtract(int number1, int number2) throws RemoteException;
	
	public int multiply(int number1, int number2) throws RemoteException;
	
	public double divide(double number1, double number2) throws RemoteException;
	
	public double power(double number1, double number2) throws RemoteException;
	
	public double ln(double number) throws RemoteException;
	
	public double sq(double number) throws RemoteException;
	
	public double reverse(double number) throws RemoteException;
}
