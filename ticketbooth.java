package ccppart1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ticketbooth {
	
	Semaphore tbSem=new Semaphore(5,true);
	boolean present; //to check the presence of staff in the booth
	String ticketboothno; //name of the booth
	ticket t;
	boolean servecust; //to check if customer is is in the booth
	
	 public ticketbooth(String ticketboothno)
	    {
	        this.ticketboothno=ticketboothno;
	        present=true;
	    }
	
	public void washroom(){
		present = false;
		System.out.println("*** The staff of booth " +ticketboothno+" is going for a washroom break. ***");
	}
	
	public void returned() {
		present = true;
		System.out.println("*** The staff of booth "+ticketboothno+" has returned. ***");
	}
	
	public ticket getTicket(customer c, String destination)
    {
		 int rand=new Random().nextInt(100); //probability of staff to go is 100%
         if(rand==0)
         {
        	 washroom();
        	 
        	 try {
        	 Thread.sleep(300);
        	 }
        	 catch(Exception e){}
        	 returned();
        }
        if(tbSem.availablePermits()==0)
        {
            System.out.println("Ticket booth "+ticketboothno+" is occupied.... Customer "+c.custID+" is queueing");
        }
        try
        {
            tbSem.acquire();
        }
        catch(Exception e){}
        if(present==false)
        {
            System.out.println("Customer "+c.custID+" has exited the queue at booth "+ticketboothno);
            tbSem.release();
            return null;
        }
        else
        {
            servecust=true;
            System.out.println("Customer "+c.custID+" is at booth "+ticketboothno);
            try
            {
                Thread.sleep(8000);
            }
            catch(Exception e){}
            System.out.println("Customer "+c.custID+" received the ticket to Gate "+destination+" in 8 seconds.");
            System.out.println("Customer "+c.custID+" has left booth "+ticketboothno+ " and has moved to the waiting area.");
            servecust=false;
            tbSem.release();
            return t;
        }
        
}
}


