package com.te.studentmanagementsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import com.te.studentmanagementsystem.InvaliduserInput;

public class StudentTest {
	static Object o;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student student = new Student(111, "Mayank", "odisha", 123644579, "ml12@gmail.com", 2560);
		Student student2 = new Student(131, "Anshul", "chennai", 789546621, "an23@gmail.com", 3210);
		Student student3 = new Student(121, "Solanki", "pune", 897852231, "sj27@gmail.com", 1560);
		Student student4 = new Student(101, "Pratik", "bangalore", 56789421, "agh2@gmail.com", 3420);
		ArrayList<Student> ar = new ArrayList<Student>();

		ar.add(student);
		ar.add(student2);
		ar.add(student3);
		ar.add(student4);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome To Student Management System");
		int i = 1;
		while (i == 1) {

			System.out.println("Kindly select sorting order");
			System.out.println();
			System.out.println("Press 1 for sort by name");
			System.out.println("Press 2 for sort by roll number");
			System.out.println("Press 3 for sort by marks");

			i = scanner.nextInt();
			
				try {
					if (i < 1 || i > 3) 
					throw new InvaliduserInput("Invalid user input");

				}
				// throw new InvaliduserInput("error");
				catch (InvaliduserInput e) {
					
					   System.out.println(e.getStackTrace());
					   System.out.println(e.toString());
					//System.out.println(e.getMessage());
					
				}
			
			/*
			 * System.out.println("Unsorted");
			 * 
			 * 
			 * for (int i = 0; i < ar.size(); i++) System.out.println(ar.get(i));
			 */

			if (i == 2) {
				Collections.sort(ar, new Sortbyroll());

				System.out.println("\nSorted by rollno");

				for (Iterator iterator = ar.iterator(); iterator.hasNext();) {
					System.out.println(iterator.next());
				}

			}

			if (i == 1) {
				Collections.sort(ar, new Sortbyname());
				System.out.println("\nSorted by name");
				for (Iterator iterator = ar.iterator(); iterator.hasNext();) {
					System.out.println(iterator.next());

				}
			}

			if (i == 3) {
				Collections.sort(ar, new Sortbymarks());

				System.out.println("\nSorted by marks");

				for (Iterator iterator = ar.iterator(); iterator.hasNext();) {
					System.out.println(iterator.next());

				}
			}
			int i2;
			do {

				System.out.println("To continue press 1 \n For exist press 0");
				i2 = scanner.nextInt();

				try {
					if (i2 > 1)
						throw new InvaliduserInput("Incorrect user input. Please enter valid number");

				}
				// throw new InvaliduserInput("error");
				catch (InvaliduserInput e) {
					
                        System.out.println(e.getStackTrace());
                        System.out.println(e.toString());
					// TODO Auto-generated catch block
					//System.out.println(e.getMessage());
					
				}
			} while (i2 > 1);

			if (i2 == 0) {
				i = 0;
				System.out.println();
				System.out.println("Thank You For Visit");

			break;
			} else {
				i = 1;
			}

		}
	
}}

