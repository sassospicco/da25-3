package da25_3.test.sync;

import java.rmi.RemoteException;
import java.util.ArrayList;

import da25_3.entities.Headquarters;
import da25_3.interfaces.EndCallback;
import da25_3.interfaces.HeadquartersInterface;
import da25_3.people.General;
import da25_3.people.SyncCommander;
import da25_3.people.SyncGeneral;

public class StandardTest implements EndCallback {
	public HeadquartersInterface hq;
	public ArrayList<General> gRef = new ArrayList<>();
	
	public void run(boolean value, int commanderLoyalty, int loyalCount, int disloyalCount, int disloyalType, int f) {
		Headquarters.unbindInstance();
		
		hq = Headquarters.getInstance(this);
		
		try {
    		if (hq.getCount() != 0) {
    			throw new RuntimeException("This test expects empty headquarters.");
    		}
    		
    		SyncCommander commander = new SyncCommander(commanderLoyalty, value);
    		gRef.add(commander);
			commander.register(hq);
    		
    		for (int i = 0; i < loyalCount; i++) {
				SyncGeneral general = new SyncGeneral(General.LOYAL);
				gRef.add(general);
				general.register(hq);
			}
    		
    		for (int i = 0; i < disloyalCount; i++) {
    			SyncGeneral general = new SyncGeneral(disloyalType);
    			gRef.add(general);
    			general.register(hq);
			}
    		
    		hq.lockAndStart(f);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			synchronized (this) {
				this.wait();
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	@Override
	public void terminate() {
		synchronized (this) {
			this.notify();
		}
	}
}
