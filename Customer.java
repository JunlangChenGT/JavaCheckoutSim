package h09;

import java.util.Random;

public class Customer implements Comparable{
	
	// Customer class variables
			int arrivalTime;
			int serviceTime;
			int waitTime;
			int indexInCustomersArray;
			
			// Customer constructor
			public Customer() {
				Random rand = new Random();
				arrivalTime = rand.nextInt(0, 86101);
				serviceTime = rand.nextInt(30, 301);
			}

			@Override
			public int compareTo(Object o) {
				if(o.getClass() != this.getClass()) throw new IllegalStateException("invalid object comparison");
				Customer customer = (Customer) o;
				
				return this.arrivalTime - customer.arrivalTime;
			}

			public static void main(String[] args)
			{
				Customer c1 = new Customer();
				Customer c2 = new Customer();
				System.out.println("Customer1 arrival time: " + c1.arrivalTime);
				System.out.println("customer2 arrival time: " + c2.arrivalTime);
				System.out.println(c1.compareTo(c2));
			}
}