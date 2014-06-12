package da25_3.people;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import da25_3.entities.Message;
import da25_3.entities.Node;
import da25_3.entities.SyncMessage;

public class SyncGeneral extends General {
	public static final long ROUND_DURATION = 500;

	protected int round = 0;
	protected Node record = new Node(true);

	public SyncGeneral(int loyalty) {
		super(loyalty);
	}

	@Override
	public void deliver(Message m) {
		final SyncMessage sm = (SyncMessage) m;
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					if (round == sm.round) {
						switch (round) {
						case 0:
							record.value = sm.content.value;
							break;
						case 1:
							record.adopt(sm.from, new Node(sm.content.value));
							break;
						default:
							sm.content.deleteId(id);
							record.adopt(sm.from, sm.content);
							break;
						}
					} else {
						throw new RuntimeException("Round duration exceeded.");
					}
				}
			}
		});
	}

	@Override
	public void start(int f, int count) throws RemoteException {
		super.start(f, count);
		
		for (int i = 1; i < count; i++) {
			if (i != id) {
				record.adopt(i, new Node(false));
			}
		}
		
		scheduleRound();
	}

	protected void executeRound() {
		synchronized (lock) {
			if (round < f) {
				round++;
				
				try {
	    			for (int i = 1; i < count; i++) {
	    				if (i != id) {
	    					SyncMessage m;
	    					if (loyalty == DISLOYAL_TRAITOR) {
	    						m = new SyncMessage(id, i, round, new Node(!record.value));
	    					} else {
	    						m = new SyncMessage(id, i, round, record);
	    					}
	    					
	    					double x;
	    					
	    					switch (loyalty) {
							case DISLOYAL_SILENT:
								x = 0;
								break;
							case DISLOYAL_SPOTTY:
								Random r = new Random();
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
	    			}
				} catch (RemoteException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
				scheduleRound();
			} else {
				if (loyalty == General.LOYAL) {
					println("Decision is "+record.decide()+" after round "+round);
				}
				
				executor.schedule(new Runnable() {
					@Override
					public void run() {
						if (id == 1) {
							try {
								hq.terminate();
							} catch (Exception e) {
							}
						}
					}
				}, ROUND_DURATION, TimeUnit.MILLISECONDS);
			}
		}
	}

	protected void scheduleRound() {
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				SyncGeneral.this.executeRound();
			}
		}, ROUND_DURATION, TimeUnit.MILLISECONDS);
	}
}
