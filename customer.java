package ccppart1;

import java.util.Random;

public class customer implements Runnable{
	
	int custID;
    ticket t=null;
    String destination;
    
    terminal bt;
    ticketbooth tc1;
    ticketbooth tc2;
    ticketmachine tm;
    waiting wa1;
    waiting wa2;
    waiting wa3;
    waiting wacur;
    
     
    public customer(int custID, terminal bt, ticketbooth tc1, ticketbooth tc2, ticketmachine tm, waiting wa1, waiting wa2, waiting wa3)
    {
        this.custID=custID;
        this.bt=bt;
        this.tc1=tc1;
        this.tc2=tc2;
        this.tm=tm;
        this.wa1=wa1;
        this.wa2=wa2;
        this.wa3=wa3;
     
    }
    
	public void run() {
	System.out.println("New customer "+custID+" is coming to the terminal.");
        
        //enter terminal
        bt.enterterminal(this);
        int rand=new Random().nextInt(3); //this is for the customer to choose the destination
        if(rand==0)
        {
            destination="1";
            wacur=wa1;
        }
        else if(rand==1)
        {
            destination="2";
            wacur=wa2;
        }
        else
        {
            destination="3";
            wacur=wa3;
        }
        
        
        try
        {
            Thread.sleep(1000);
        }
        
        catch(Exception e){}
        //get ticket
        while(t==null) //if customer doesn't receive any ticket, they will loop this
        {
            rand = new Random().nextInt(3); //to choose between the booth and the machine
            if(rand==0) //go to the machine
            {
                if(tm.present==true)
                {
                    try {
						t=tm.getTicket(this, destination);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
            else if(rand==1) //to go to booth 1
            {
                if(tc1.present==true)
                {
                    t=tc1.getTicket(this, destination);
                }
            }
            else if(rand==2) //to go to booth 2
            {
                if(tc2.present==true)
                {
                    t=tc2.getTicket(this, destination);
                }
            }
            else
            {
                try
                {
                    Thread.sleep(15);
                }
                catch(Exception e){}
            }
        }
        
        try
        {
            Thread.sleep(2500);
        }
        catch(Exception e){}
        
        //enter waiting area
        System.out.println("Customer "+custID+" is going to the waiting area.");
        
    }
}
