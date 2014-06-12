package da25_3.people;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import da25_3.interfaces.GeneralInterface;
import da25_3.interfaces.HeadquartersInterface;

public abstract class General implements GeneralInterface {
	public static final int LOYAL = 0;
	public static final int DISLOYAL_SILENT = 1;
	public static final int DISLOYAL_SPOTTY = 2;
	public static final int DISLOYAL_TRAITOR = 3;
	
	protected int id;
	protected int count;
	protected int f;
	protected HeadquartersInterface hq;
	protected ScheduledThreadPoolExecutor executor;
	protected Object lock = new Object();
	protected int loyalty;
	
	public General(int loyal) {
		this.loyalty = loyal;
	}
	
	public void register(HeadquartersInterface hq) {
		try {
			GeneralInterface stub = (GeneralInterface) UnicastRemoteObject.exportObject(this, 0);
			this.hq = hq;
			this.id = hq.register(stub);
		} catch (RemoteException e) {
			System.out.println("Unable to register: RemoteException.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void start(int f, int count) throws RemoteException {
		this.f = f;
		this.count = count;
		
		executor = new ScheduledThreadPoolExecutor(count + 1);
	}
	
	public void println(Object o) {
		String s = (String) o;
		
		if (id != 1 && !s.substring(0, 8).equals("Decision")) {
			return;
		}
		
		System.out.println("General "+id+": "+o);
	}
}
