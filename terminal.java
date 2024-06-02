package ccppart1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class terminal extends Thread {
	Semaphore btSem=new Semaphore(15,true); //15 customers can be inside the terminal at a time
	Semaphore enterSem=new Semaphore(1,true); //customer enter terminal 1 by 1
	ticketbooth tc1;
	ticketbooth tc2;
	ticketmachine tm;
	waiting wa1;
	waiting wa2;
	waiting wa3;
	
	boolean open;
	boolean present;
	int custID=1;
	
	public static void main(String[] args) 
	{
	    terminal bt=new terminal();
	    bt.start();
	}

	public terminal() {
		System.out.println("\t\t\t\tWelcome to Asia Pacific Bus Terminal\n\n");
		open = true;
		tm = new ticketmachine();
		tc1 = new ticketbooth("1");
		tc2 = new ticketbooth("2");
		wa1= new waiting("1");
		wa2= new waiting("2");
		wa3=new waiting("3");
	}

	@Override
	public void run() {
		ExecutorService terminal=Executors.newCachedThreadPool();
		terminal.submit(new ticketstaff(tc1));
		terminal.submit(new ticketstaff(tc2));
		
		
		while(true) {
			terminal.submit(new customer(custID, this, tc1, tc2, tm, wa1, wa2, wa3));
			custID++;
			try {
				Thread.sleep(1000*(1+new Random().nextInt(3)));
			}
			catch(Exception e){}
		}
		
	}

	public void enterterminal(customer c) {
		// TODO Auto-generated method stub
		 if(open==false)
	        {
	            System.out.println("**** The guard is blocking the entrance... Customer "+c.custID+" is waiting... (Terminal population: "+(15-btSem.availablePermits())+") ****");
	        }
	        try
	        {
	            enterSem.acquire(); //only one customer go throught the gate at a time
	            btSem.acquire(); //this is the semaphore for the terminal sem(150)
	            Thread.sleep(10);
	            System.out.println("Customer "+c.custID+" has entered the Bus Terminal (Terminal population: "+(15-btSem.availablePermits())+")");
	            Thread.sleep(100); //customer take 0.1 second to enter the Bus Terminal
	           enterSem.release();
	        }
	        catch(Exception e){} 
	}
	
	public void block()
    {
        open=false;
        System.out.println("*** Terminal entrance is blocked. ***");
        try 
        {
            btSem.acquire(20); //acquire(20) is used to block the entrance of 
                                //the terminal until at least 20 customer leave the terminal
            System.out.println("**** Terminal entrance is open now. ****");
            btSem.release(20); //release directly after so that customer can enter
        }
        catch(Exception e){}

        open=true;
    }
  
    public void leaveTerminal(customer c)
    {
        System.out.println("Customer "+c.custID+" is leaving the Bus Terminal");
        btSem.release();
    }
}

