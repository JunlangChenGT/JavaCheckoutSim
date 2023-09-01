package h09;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class QueueSimulator {

	public static void simulator(boolean singleQueue, int numCustomers) {

		// ================================================================
		// =========== STEP 1: Initialize simulation variables ============
		// ================================================================

		// Create array of customers
		Customer[] customers = new Customer[numCustomers];
		for(int i = 0; i < customers.length; i++) {
			customers[i] = new Customer();
			//System.out.println(customers[i].arrivalTime);
		}

		// Sort customers
		Arrays.sort(customers);
		//assign class variable indexInCustomersArray
		for(int i = 0; i < customers.length; i++)
		{
			customers[i].indexInCustomersArray = i;
			//System.out.println(customers[i].indexInCustomersArray);
		}

		// Create array for cashiers/servers
		int[] cashiers = new int[10];
		for(int i = 0; i < cashiers.length; i++) cashiers[i] = -7;
		

		
		
		// Create queues
		Queue<Customer>[] queues;
		if(singleQueue) {	// If singleQueue is true, we only create 1 queue
			queues = new LinkedList[1];
			// TODO: Initialize your LinkedList objects
			queues[0] = new LinkedList();
		}
		else {	// Otherwise we create 10 queues
			queues = new LinkedList[10];
			// TODO: Initialize your LinkedList objects
			for(int i = 0; i < queues.length; i++) queues[i] = new LinkedList();
			
		}
		
		/* TODO:
		 * You will likely want some additional variables not included here.
		 * For example, a variable to keep an index reference in your 'customers' array
		 * in order to determine which customer will be the next to arrive. Also a variable
		 * to keep track of the total waiting time of the customers.
		 */
		int[] waitTimeArray = new int[numCustomers];
		int totalCustomersServed = 0;
		int nextCustomerNo = 0;
		long totalWaitTimeForCustomersServed = 0;
		Customer[] customersBeingServed = new Customer[10];

		// ================================================================
		// ======== STEP 2: Simulate the day, iterating by second =========
		// ================================================================

		// Create a loop to iterate through every second of the day
		if(singleQueue)
		{
			for(int currentTime = 0; currentTime <= 86400; currentTime++) 
			{

				// ================================================================
				// = STEP 3: Add any arriving customers to the appropriate queue ==
				// ================================================================
				

				/* TODO:
				 * If the next customer in the 'customers' array is arriving at this timestep,
				 * (the customer's 'arrivalTime' == currentTime), add them to the appropriate queue.
				 * If singleQueue is true, they should be added to queues[0].
				 * If singleQueue is false, you would need to determine which queue has the smallest size,
				 * and then add the customer to that queue.
				 */
				
				//if the next customer's arrival time lines up with current time, then add the customer to the
				//queue and start the stopwatch and increment the next customer index
				while(nextCustomerNo < numCustomers && customers[nextCustomerNo].arrivalTime == currentTime) 
				{
					queues[0].offer(customers[nextCustomerNo]);
					//System.out.println("currentTime: " + currentTime);
					customers[nextCustomerNo].waitTime = currentTime; //temporarily stored
					nextCustomerNo++;
					//System.out.println(customers[nextCustomerNo].arrivalTime);
				}
				
				
				

				// ================================================================
				// == STEP 4: Have cashiers get next customer if they are done  ===
				// ================================================================
				
				//pull the customer from the line
				for(int i = 0; i < cashiers.length; i++)
				{
					if( (cashiers[i] == -7) && (customersBeingServed[i] == null) && !queues[0].isEmpty() )
					{
						//System.out.println("b" + i);
						customersBeingServed[i] = queues[0].poll();
						cashiers[i] = currentTime + customersBeingServed[i].serviceTime;
						customersBeingServed[i].waitTime = currentTime - customersBeingServed[i].waitTime;
						//get wait time by subtracting previous time by current time
						waitTimeArray[customersBeingServed[i].indexInCustomersArray] = customersBeingServed[i].waitTime;
						//add the time to the array
					}
					
					if( (customersBeingServed[i] != null) && (currentTime == cashiers[i])) 
					{
							
							cashiers[i] = -7;
							totalCustomersServed++;
							customersBeingServed[i] = null;
					}
					
				}
				

				/* TODO:
				 * Loop through the 'cashiers' array, check if any element matches 'currentTime',
				 * which would mean the cashier has finished with a customer at this time.
				 * If so, they should poll() from the appropriate queue ,
				 * (queues[0] if singleQueue is true, or the same 'queues' index as the 'cashiers' index if singleQueue is false)
				 * and update the 'cashiers' index with what time the cashier will be done with their new customer
				 * (currentTime + the customer's 'serviceTime').
				 * Additionally, add the amount of time the customer has been waiting ('currentTime' - customer's 'arrivalTime')
				 * to the cumulative total waiting time.
				 */
			}
		}
		else if(!singleQueue)
		{
			for(int currentTime = 0; currentTime <= 86400; currentTime++) 
			{

				// ================================================================
				// = STEP 3: Add any arriving customers to the appropriate queue ==
				// ================================================================

				//if the next customer's arrival time lines up with current time, then add the customer to the
				//queue and start the stopwatch and increment the next customer index
				while(nextCustomerNo < numCustomers && customers[nextCustomerNo].arrivalTime == currentTime) 
				{
					int shortestLine = 0;
					for(int i = 1; i < queues.length; i++)
					{
						if(queues[i].size() < queues[shortestLine].size())
						{
							shortestLine = i;
						}
						
					}
					queues[shortestLine].offer(customers[nextCustomerNo]);
					//System.out.println("currentTime: " + currentTime);
					customers[nextCustomerNo].waitTime = currentTime;
					nextCustomerNo++;
					//System.out.println(customers[nextCustomerNo].arrivalTime);
				}
				
				
				

				// ================================================================
				// == STEP 4: Have cashiers get next customer if they are done  ===
				// ================================================================
				
				//pull the customer from the line
				for(int i = 0; i < cashiers.length; i++)
				{
					if( (cashiers[i] == -7) && (customersBeingServed[i] == null) && !queues[i].isEmpty() )
					{
						//System.out.println("b" + i);
						customersBeingServed[i] = queues[i].poll();
						cashiers[i] = currentTime + customersBeingServed[i].serviceTime;
						customersBeingServed[i].waitTime = currentTime - customersBeingServed[i].waitTime;
						//get wait time by subtracting previous time by current time
						waitTimeArray[customersBeingServed[i].indexInCustomersArray] = customersBeingServed[i].waitTime;
						//add the time to the array
					}
					
					if( (customersBeingServed[i] != null) && (currentTime == cashiers[i])) 
					{
							
							cashiers[i] = -7;
							totalCustomersServed++;
							customersBeingServed[i] = null;
					}
					
				}
			}
		}


		// ================================================================
		// ========= STEP 5: Give the results of the simulation  ==========
		// ================================================================
		
		/* TODO:
		 * If the method is a void type, print the results.
		 * Alternatively, return the total waiting time if the method return type is 'int' or 'long'.
		 */
		for(int i = 0; i < totalCustomersServed; i++) totalWaitTimeForCustomersServed += waitTimeArray[i];
		double averageWaitTime = (double) totalWaitTimeForCustomersServed / (double) totalCustomersServed;
		if(singleQueue) System.out.println("Single Queue");
		else System.out.println("10 Queue");
		System.out.println("Total customers in sim: " + numCustomers);
		System.out.println("Customers served: " + totalCustomersServed);
		System.out.println("Total time spent waiting in line before cashier service: " + totalWaitTimeForCustomersServed);
		System.out.println("Average time waiting in line per customer: " + averageWaitTime);
		
		
	}

	public static void main(String[] args) {
		
		// Test the simulation
		QueueSimulator queuesim = new QueueSimulator();
		queuesim.simulator(true, 5000);
		queuesim.simulator(false, 5000);
		//single queue total 414k to 1.5kk person: 83-99
		//multi queue total 1.1kk to 1.9kk person: 223-387
	}

}