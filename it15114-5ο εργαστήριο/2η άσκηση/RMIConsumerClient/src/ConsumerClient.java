import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConsumerClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		
		//������� ��� ������� ��� ����������� ��� �� ������������� ��� ��� ������������
		//��� �� ����� �� ���� ���������
		int consumerDelay = 100;
        int noCons = 2;
        Consumer cons[] = new Consumer[noCons];
        
        try {
			//������ ��� registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			//������ ��� �������������� ������������
			String rmiObjectName = "Buffer";
			Buffer ref = (Buffer)registry.lookup(rmiObjectName);
			
			//���������� �������-�����������
            for (int i=0; i<noCons; i++) {
            	//������� ��� �������������� ������������ ��� ������ ��� �� 
            	//������� �� ������ ��� �������������� �������� ��� �����
            	cons[i] = new Consumer(consumerDelay, ref);
            	cons[i].start();
            }
			
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
		
	}

}

//����� �����������
class Consumer extends Thread {
	private Buffer buffer;
       private int scale;

	public Consumer(int scale, Buffer buff) {
		this.scale = scale;
		buffer = buff;
	}

	public void run() {
		int value;
		while (true) {
			try {
				sleep((int)(Math.random()*scale));
			} catch (InterruptedException e) { }
			try {
				//����� ��� �������������� ������� 
				value = buffer.get();
				//�������� ��� ����� ��� ������������ ��� ��� �������� buffer
				System.out.println("Consumer " + Thread.currentThread().getName() + " just consumed the item " + value);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}		
	}
}
