package com.te.studentmanagementsystem;

import java.io.*;
import java.lang.*;
import java.util.*; 

public class Student {
	int rollno;
    String name;
    String address;
    int phnumber;
    String mailid;
    int marks;
    
 
   
    public Student(int rollno, String name, String address, int phnumber,String mailid,int marks )
    {
    	
    	
        this.rollno = rollno;
        this.name = name;
        this.address = address;
        this.phnumber=phnumber;
        this.mailid=mailid;
        this.marks=marks;
    }



	@Override
	public String toString() {
		return "Student [rollno=" + rollno + ", name=" + name + ", address=" + address + ", phnumber=" + phnumber
				+ ", mailid=" + mailid + ", marks=" + marks + "]";
	}
  
   
  
}
 

class Sortbyroll implements Comparator<Student> {
 
    public int compare(Student a, Student b)
    {
 
        return a.rollno - b.rollno;
    }
}
 

class Sortbyname implements Comparator<Student> {
 
   
    public int compare(Student a, Student b)
    {
 
        return a.name.compareTo(b.name);
    }

}
class Sortbymarks implements Comparator<Student>{

	@Override
	public int  compare(Student o1, Student o2) {
		// TODO Auto-generated method stub
		return o1.marks-o2.marks;
	}
	
}
 
  
    