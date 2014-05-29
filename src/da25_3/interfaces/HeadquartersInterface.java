package da25_3.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HeadquartersInterface extends Remote {
	public int register(GeneralInterface stub) throws RemoteException;
	
	public GeneralInterface get(int id) throws RemoteException;
	
	public int getCount() throws RemoteException;
	
	public void lockAndStart(int f) throws RemoteException;
	
	public void terminate() throws RemoteException;
}
