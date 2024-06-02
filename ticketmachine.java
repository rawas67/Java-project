package ccppart1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ticketmachine {
	Semaphore tmSem=new Semaphore(5,true);
	boolean present;
	ticket t;
	int custID;
	
	public ticketmachine(){
		present=true;
	}
	
	 public void OOS()
	    {
	        present=false;
	        System.out.println("***** TICKET MACHINE IS OUT OF SERVICE AND CANNOT BE USED AT THE MOMENT *****");
	    }
	    
	    public void INS()
	    {
	        System.out.println("*** TICKET MACHINE IS IN SERVICE NOW ***");
	        present=true;
	    }
	    
	    public ticket getTicket(customer c, String destination)
	    {
			if(tmSem.availablePermits()==0)
			{
				System.out.println("Ticket Machine is in-use.... Customer "+c.custID+" is queuing for ticket machine...");
	        }
	        try
	        {
	            tmSem.acquire();
	        }
	        catch(Exception e){}
	        if(present==false)
	        {
	            System.out.println("Customer "+c.custID+" has exited the queue at the ticket machine");
	            tmSem.release();
	            return null;
	        }
	        else
	        {
	            System.out.println("Customer "+c.custID+" is using the ticket machine");
	            try
	            {
	                Thread.sleep(4000);
	            }
	            catch(Exception e){}
	            //the ticket machine can only be broken when in use
	            int rand=new Random().nextInt(100); //probability of ticket machine going out of service is 10%
	            if(rand==0)
	            {
	                OOS();
	                System.out.println("Customer "+c.custID+" did not receive any ticket and is leaving.");
	                tmSem.release();
	                INS();
	                return null;
	            }
	            try
	            {
	                Thread.sleep(1000);
	            }
	            catch(Exception e){}	            
	            System.out.println("Customer "+c.custID+" has received the ticket to Gate "+destination+" in 4 seconds.");
	            System.out.println("Customer "+c.custID+" has left the machine.");
	            tmSem.release();
	            return t;
	        }
	    }
}
