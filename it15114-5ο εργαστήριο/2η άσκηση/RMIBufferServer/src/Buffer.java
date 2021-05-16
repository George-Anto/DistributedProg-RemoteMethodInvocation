import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Buffer extends Remote {
	
	//Υπογραφές των απομακρυσμένων μεθόδων που θα καλούν οι παραγωγοί και οι καταναλωτές
	
	public void put(int data) throws RemoteException;
	
	public int get() throws RemoteException;
}
