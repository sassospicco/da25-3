package da25_3.people;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import da25_3.entities.Message;
import da25_3.entities.RbaMessage;

public class RbaGeneral extends General {
	public static final long MAX_DELAY = 100;
	public static final int PH_NOTIFICATION = 1;
	public static final int PH_PROPOSAL = 2;
	public static final int PH_DECISION = 3;
	
	protected int round = 1;
	protected int phase = PH_NOTIFICATION;
	protected boolean decided = false;
	protected boolean stopped = false;
	protected boolean v;
	
	protected int trueCount = 0;
	protected int falseCount = 0;
	protected int questionCount = 0;
	
	public RbaGeneral(int loyal) {
		super(loyal);
	}

	@Override
	public void deliver(Message rm) throws RemoteException {
		if (stopped) {
			return;
		}
		
		final RbaMessage m = (RbaMessage) rm;
		
		long rand = (long) (new Random().nextFloat() * MAX_DELAY);
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				performDeliver(m);
			}
		}, rand, TimeUnit.MILLISECONDS);
	}
	
	protected void performDeliver(final RbaMessage m) {
		synchronized (lock) {
			if (stopped || m.round < round || (m.round == round && m.phase < phase)) {
				return;
			}
			
			if (m.round != round || m.phase != phase) {
				executor.schedule(new Runnable() {
					@Override
					public void run() {
						performDeliver(m);
					}
				}, MAX_DELAY, TimeUnit.MILLISECONDS);
				
				return;
			}
			
			switch (phase) {
			case PH_NOTIFICATION:
				switch (m.value) {
				case RbaMessage.VALUE_TRUE:
					trueCount++;
					break;
				case RbaMessage.VALUE_FALSE:
					falseCount++;
					break;
				}
				
				if (trueCount + falseCount >= count - f) {
					proposalPhase();
				}
				break;
			case PH_PROPOSAL:
				switch (m.value) {
				case RbaMessage.VALUE_TRUE:
					trueCount++;
					break;
				case RbaMessage.VALUE_FALSE:
					falseCount++;
					break;
				case RbaMessage.VALUE_QUESTION:
					questionCount++;
					break;
				}
				
				if (trueCount + falseCount + questionCount >= count - f) {
					decisionPhase();
				}
				break;
			}
		}
	}
	
	protected void notificationPhase() {
		println("Round "+round+" is starting.");
		
		synchronized (lock) {
			phase = PH_NOTIFICATION;
			
			for (int i = 0; i < count; i++) {
				int vi = v ? RbaMessage.VALUE_TRUE : RbaMessage.VALUE_FALSE;
				
				if (loyalty == General.DISLOYAL_TRAITOR) {
					vi = i % 2 == 0 ? RbaMessage.VALUE_TRUE : RbaMessage.VALUE_FALSE;
				}
				
				RbaMessage m = new RbaMessage(id, i, PH_NOTIFICATION, round, vi);
				
				try {
					hq.get(i).deliver(m);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
			
			trueCount = 0;
			falseCount = 0;
		}
	}
	
	protected void proposalPhase() {
		synchronized (lock) {
			phase = PH_PROPOSAL;
			
			int w = RbaMessage.VALUE_QUESTION;
			
			if (trueCount > (count + f) / 2D) {
				w = RbaMessage.VALUE_TRUE;
			}
			
			if (falseCount > (count + f) / 2D) {
				w = RbaMessage.VALUE_FALSE;
			}
			
			for (int i = 0; i < count; i++) {
				if (loyalty == General.DISLOYAL_SPOTTY && new Random().nextBoolean()) {
					continue;
				}
				
				if (loyalty == General.DISLOYAL_TRAITOR) {
					w = i % 2 == 0 ? RbaMessage.VALUE_TRUE : RbaMessage.VALUE_FALSE;
				}
				
				RbaMessage m = new RbaMessage(id, i, PH_PROPOSAL, round, w);
				
				try {
					hq.get(i).deliver(m);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
			
			if (decided) {
				stopped = true;
				
//				executor.schedule(new Runnable() {
//					@Override
//					public void run() {
//						try {
//							hq.terminate();
//						} catch (RemoteException e) {
//						}
//					}
//				}, 3 * MAX_DELAY, TimeUnit.MILLISECONDS);
			}
			
			trueCount = 0;
			falseCount = 0;
			questionCount = 0;
		}
	}
	
	protected void decisionPhase() {
		synchronized (lock) {
			phase = PH_DECISION;
			
			if (trueCount > f || falseCount > f) {
    			if (trueCount > falseCount) {
    				v = true;
    			} else {
    				v = false;
    			}
    			
    			if ((trueCount > 3*f || falseCount > 3*f) && loyalty == General.LOYAL) {
    				decided = true;
    				
    				println("Decision is "+v+" at round "+round);
    			}
			} else {
				Random rand = new Random();
				v = rand.nextBoolean();
			}
			
			trueCount = 0;
			falseCount = 0;
			questionCount = 0;
			
			round++;
			
			notificationPhase();
		}
	}
	
	@Override
	public void start(int f, int count) throws RemoteException {
		super.start(f, count);
		
		if (loyalty == General.DISLOYAL_SILENT) {
			stopped = true;
			return;
		}
		
		Random rand = new Random();
		v = rand.nextBoolean();
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				notificationPhase();
			}
		}, MAX_DELAY, TimeUnit.MILLISECONDS);
	}
}
