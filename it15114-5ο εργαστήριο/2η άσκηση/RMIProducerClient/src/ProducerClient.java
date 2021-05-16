import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ProducerClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		
		//Ορισμός του αριθμού των παραγωγών που θα δημιουργηθούν, των επαναλήψεων
		//που θα κάνει ο καθένας και της καθυστέρησης που θα έχουν σε κάθε επανήληψη
		int noProds = 3;
		Producer prod[] = new Producer[noProds];
		int noIterations = 20;
		int producerDelay = 1;
		
		try {
			//Εύρεση του registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			//Εύρεση του απομακρυσμένου αντικειμένου
			String rmiObjectName = "Buffer";
			Buffer ref = (Buffer)registry.lookup(rmiObjectName);
			
			//Δημιουργία των νημάτων-παραγωγών
            for (int i=0; i<noProds; i++) {
            	//Πέρασμα του απομακρυσμένου αντικειμένου στα νήματα για να 
            	//μπορούν να καλούν τις απομακρυσμένες μεθόδους επί αυτού
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

//Κλάση παραγωγού
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
				//Δημιουργία ψευδοτυχαίας τιμής που θα περάσει ο παραγωγός στον buffer
				Random rand = new Random();
				int randomInt = rand.nextInt(100);
				//Κλήση της απομακρυσμένης μεθόδου
				buffer.put(randomInt);
				//Εμφάνιση της τιμής που παράγει και τοποθετεί στον buffer ο κάθε παραγωγός
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
