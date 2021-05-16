import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Buffer extends Remote {
	
	//Υπογραφές των απομακρυσμένων μεθόδων
	
	public void put(int data) throws RemoteException;
	
	public int get() throws RemoteException;
}
