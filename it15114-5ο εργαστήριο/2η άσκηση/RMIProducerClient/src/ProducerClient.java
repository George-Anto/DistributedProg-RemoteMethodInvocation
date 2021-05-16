import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ProducerClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		
		//������� ��� ������� ��� ��������� ��� �� �������������, ��� �����������
		//��� �� ����� � ������� ��� ��� ������������ ��� �� ����� �� ���� ���������
		int noProds = 3;
		Producer prod[] = new Producer[noProds];
		int noIterations = 20;
		int producerDelay = 1;
		
		try {
			//������ ��� registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			//������ ��� �������������� ������������
			String rmiObjectName = "Buffer";
			Buffer ref = (Buffer)registry.lookup(rmiObjectName);
			
			//���������� ��� �������-���������
            for (int i=0; i<noProds; i++) {
            	//������� ��� �������������� ������������ ��� ������ ��� �� 
            	//������� �� ������ ��� �������������� �������� ��� �����
            	prod[i] = new Producer(noIterations, producerDelay, ref);
            	prod[i].start();
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

//����� ���������
class Producer extends Thread{
	
	private int reps;
	private int scale;
	private Buffer buffer;
	
	public Producer(int reps, int scale, Buffer buff) {
		this.reps = reps;
		this.scale = scale;
		buffer = buff;
	}
	
	public void run() {
		for(int i = 0; i < reps; i++) {
			try {
				//���������� ������������ ����� ��� �� ������� � ��������� ���� buffer
				Random rand = new Random();
				int randomInt = rand.nextInt(100);
				//����� ��� �������������� �������
				buffer.put(randomInt);
				//�������� ��� ����� ��� ������� ��� ��������� ���� buffer � ���� ���������
				System.out.println("Producer " + Thread.currentThread().getName() + " just produced the item " + randomInt);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			try {
				sleep((int)(Math.random()*scale));
			} catch (InterruptedException e) { }
		}
	}
	
}
