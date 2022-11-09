package com.te.restaurant;

import java.util.Scanner;

public class RestaurantProject {
	public static Scanner scanner = new Scanner(System.in);

	static int[] or = new int[5];

	public static int[] order(String[] items, int[] rates) {
		boolean end = false;
		int sum = 0;

		do {
			System.out.println("Select items from the menu below: \n");
			System.out.println("\tMENU\n");

			for (int i = 0; i < items.length; i++)
				System.out.println((i + 1) + "        " + items[i] + "      " + rates[i]);

			System.out.println("\nEnter your choice: ");
			int choice = scanner.nextInt();
			if (choice > 0 && choice < 6) {

				System.out.println("your ordered: " + items[choice - 1]);
				System.out.println("Enter the quantity: ");
				int q = scanner.nextInt();

				or[choice - 1] = or[choice-1]+q;

			}else {
				try {
					throw new InvaliduserInput("Enter valid input ");
				} catch (InvaliduserInput e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			System.out.println("Do want to continue: (y/n)");
			char ch = scanner.next().charAt(0);

			if (ch == 'y') {
				order(items, rates);}
			else {
				
		}

		} while (end);

		return or;

	}

	public static void total(String[] items, int[] rates, int[] choi) {
		int sum = 0;
		System.out.println("Your Orders are:\n");
		for (int i = 0; i < choi.length; i++) {
			if (choi[i] != 0) {
				sum += choi[i] * rates[i];
				System.out.println(items[i] + "    " + choi[i] + "  =  " + rates[i] * choi[i] + "rs");
			}
		}

		System.out.println("The total bill is: " + sum);
		System.out.println("Enter the amount to pay: ");
		int payAmt = scanner.nextInt();
		if(payAmt<sum) {
			System.out.println("enter amount more or equal to bill amount");
		}
		else if(payAmt == sum) {
			System.out.println("0 balance");
		}
		else {
			sum = sum-payAmt;
			System.out.println("Change to be returned: "+sum*-1);
		}
	}
 
	public static void main(String[] args) {

		String items[] = { "Mutton biryani","Chicken Curry", "Fish fry", "Prawn masala", "green salad" };
		int rates[] = { 200, 100, 70, 260, 50 };

		int[] choi = order(items, rates);
		total(items, rates, choi);
		System.out.println("Thank you for visit");

	}

}
	
	


