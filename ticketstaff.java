package ccppart1;

import java.util.Random;

public class ticketstaff implements Runnable 
{
	ticketbooth tc;
	
	public ticketstaff (ticketbooth tc) {
		this.tc=tc;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(60000+new Random() .nextInt(120000));
			}
			catch (Exception e) {}
			while (true) {
				if (tc.tbSem.tryAcquire()==true){
					break;
				}
			}
			tc.washroom();
			tc.tbSem.release();
			try {
				Thread.sleep(30000);
			}
			catch(Exception e) {}
			tc.returned();
		}
		
		

		                                                                         
	}
}

