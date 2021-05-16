import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Buffer extends Remote {
	
	//��������� ��� �������������� �������
	
	public void put(int data) throws RemoteException;
	
	public int get() throws RemoteException;
}
