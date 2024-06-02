package ccppart1;

public class ticket {
	
		String destination;
	    
	    public ticket(String destination)
	    {
	        this.destination = destination;
	    }
	    
	    private static int ticketOrder=0;
	    
	    public synchronized int getticketnumber()
	    {
	        //the first return ticket number is 1
	        ticketOrder++;
	        return ticketOrder;
	    }
	    
}
