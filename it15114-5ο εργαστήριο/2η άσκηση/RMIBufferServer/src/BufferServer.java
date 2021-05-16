import java.rmi.server.*;
import java.rmi.registry.*;

public class BufferServer {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) throws Exception {
				
		System.setProperty("java.rmi.server.hostname", HOST);
                
		//���������� ��� �������������� ������������ ��� ���������� ��� ��� �����
		//��� �������������� �������
		Buffer robj = new BufferImpl();
		
		//���������� ��� Registry
		Registry registry = LocateRegistry.createRegistry(PORT);
		
		//�������� ��� �������������� ������������ �� ��� ����� ��� ������������� ��� ��� Registry
		String rmiObjectName = "Buffer";
		registry.rebind(rmiObjectName, robj);
		System.out.println("Remote object bounded.");
		
		//����������� ��� server �� ����� ��� enter
		System.out.println("Press <Enter> for exit.");
		System.in.read();
		
		//���������� ��� Registry
		UnicastRemoteObject.unexportObject(robj, true);
		registry.unbind(rmiObjectName);
		System.out.println("Remote object unbounded.");
	}
}