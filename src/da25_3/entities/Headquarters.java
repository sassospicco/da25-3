package da25_3.entities;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import da25_3.interfaces.EndCallback;
import da25_3.interfaces.GeneralInterface;
import da25_3.interfaces.HeadquartersInterface;

public class Headquarters implements HeadquartersInterface {
	private boolean locked = false;
	private ArrayList<GeneralInterface> generals = new ArrayList<>();
	private EndCallback endCallback;
	
	public Headquarters() {
	}
	
	public Headquarters(EndCallback endCallback) {
		this.endCallback = endCallback;
	}

	@Override
	public int register(GeneralInterface general) throws RemoteException {
		if (locked) {
			throw new RuntimeException("Headquarters is locked.");
		}
		
		generals.add(general);
		int id = generals.size() - 1;
		
		return id;
	}

	@Override
	public GeneralInterface get(int id) throws RemoteException {
		return generals.get(id);
	}

	public int getCount() throws RemoteException {
		return generals.size();
	}

	@Override
	public void lockAndStart(int f) throws RemoteException {
		locked = true;
		
		int count = generals.size();
		
		if (f >= count / 3D) {
			System.out.println("Caution! The necessary condition is not met.");
		}
		
		for (GeneralInterface g : generals) {
			g.start(f, count);
		}
	}
	
	@Override
	public void terminate() {
		if (endCallback != null) {
			endCallback.terminate();
		}
	}

	public static HeadquartersInterface getInstance(EndCallback endCallback) {
		String hqLocation = HeadquartersInterface.class.getProtectionDomain().getCodeSource().getLocation().toString();
		System.setProperty("java.rmi.server.codebase", hqLocation);
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		HeadquartersInterface hq;

		try {
			hq = (HeadquartersInterface) registry.lookup(HeadquartersInterface.class.getCanonicalName());
			hq.getCount();
		} catch (Exception e) {
			try {
				hq = new Headquarters(endCallback);
				HeadquartersInterface stub = (HeadquartersInterface) UnicastRemoteObject.exportObject(hq, 0);
				registry.rebind(HeadquartersInterface.class.getCanonicalName(), stub);
			} catch (Exception e2) {
				e.printStackTrace();
				throw new RuntimeException(e2);
			}
		}
		
		return hq;
	}
	
	public static void unbindInstance() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			registry.unbind(HeadquartersInterface.class.getCanonicalName());
		} catch (NotBoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
