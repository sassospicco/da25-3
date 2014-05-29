package da25_3.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import da25_3.entities.Message;

public interface GeneralInterface extends Remote {
	public void deliver(Message m) throws RemoteException;

	public void start(int f, int count) throws RemoteException;
}
