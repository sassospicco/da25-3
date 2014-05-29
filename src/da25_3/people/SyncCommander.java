package da25_3.people;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import da25_3.entities.Message;
import da25_3.entities.Node;
import da25_3.entities.SyncMessage;

public class SyncCommander extends SyncGeneral {
	protected boolean value;
	
	public SyncCommander(int loyalty, boolean value) {
		super(loyalty);
		
		this.value = value;
	}
	
	@Override
	public void deliver(Message m) {
		return;
	}
	
	@Override
	public void start(int f, int count) throws RemoteException {
		super.start(f, count);
	}
	
	@Override
	protected void executeRound() {
		try {
			Random r = new Random();
			
			for (int i = 1; i < count; i++) {
				SyncMessage m;
				if (loyalty == DISLOYAL_TRAITOR) {
					m = new SyncMessage(id, i, round, new Node(r.nextDouble() > 0.5));
				} else {
					m = new SyncMessage(id, i, round, new Node(value));
				}
				
				double x;
				
				switch (loyalty) {
				case DISLOYAL_SILENT:
					x = 0;
					break;
				case DISLOYAL_SPOTTY:
					x = r.nextDouble();
					break;
				default:
					x = 1;
					break;
				}
				
				if (x > 0.5) {
					hq.get(i).deliver(m);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected void scheduleRound() {
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				SyncCommander.this.executeRound();
			}
		}, ROUND_DURATION / 100, TimeUnit.MILLISECONDS);
	}
}
