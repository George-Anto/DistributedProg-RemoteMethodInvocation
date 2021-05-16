import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Υλοποίηση των μεθόδων που προσφέρονται για απομακρυσμένη κλήση στο interface Buffer
public class BufferImpl extends UnicastRemoteObject implements Buffer {
	
	//Ορίσματα που χρειάζονται για την λειτουργία του buffer
	private static final long serialVersionUID = 1;
	private int[] contents;
	private final int size;
	private int front, back;
	private volatile int counter = 0;
	private Lock lock = new ReentrantLock();
	private final Condition bufferFull = lock.newCondition();
	private final Condition bufferEmpty = lock.newCondition();

	public BufferImpl() throws RemoteException {
		this.size = 10;
		contents = new int[size];
		for (int i=0; i<size; i++)
			contents[i] = 0;
		this.front = 0;
		this.back = size - 1;
	}
	
	public void put(int data) {
		lock.lock();
			try {
				while (counter == size) {
					try {
						bufferFull.await();
					} catch (InterruptedException e) { }
				}
				back = (back + 1) % size;
				contents[back] = data;
				counter++;
				bufferEmpty.signalAll();
			} finally {
				lock.unlock() ;
			}
	}
	
	public int get() {
		int data = 0;
		lock.lock();
		try {
			while (counter == 0) {
				try {
					bufferEmpty.await();
				} catch (InterruptedException e) { }
			}
			data = contents[front];
			front = (front + 1) % size;
			counter--;
			bufferFull.signalAll();
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}
