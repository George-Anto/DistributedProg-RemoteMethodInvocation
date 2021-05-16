import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConsumerClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		
		//Ορισμός του αριθμού των καταναλωτών που θα δημιουργηθούν και της καθυστέρησης
		//που θα έχουν σε κάθε επανάληψη
		int consumerDelay = 100;
        int noCons = 2;
        Consumer cons[] = new Consumer[noCons];
        
        try {
			//Εύρεση του registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			//Εύρεση του απομακρυσμένου αντικειμένου
			String rmiObjectName = "Buffer";
			Buffer ref = (Buffer)registry.lookup(rmiObjectName);
			
			//Δημιουργία νημάτων-καταναλωτών
            for (int i=0; i<noCons; i++) {
            	//Πέρασμα του απομακρυσμένου αντικειμένου στα νήματα για να 
            	//μπορούν να καλούν τις απομακρυσμένες μεθόδους επί αυτού
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

//Κλάση καταναλωτών
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
				//Κλήση της αποκακρυσμένης μεθόδου 
				value = buffer.get();
				//Εμφάνιση της τιμής που καταναλώθηκε από τον εκάστοτε buffer
				System.out.println("Consumer " + Thread.currentThread().getName() + " just consumed the item " + value);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}		
	}
}
