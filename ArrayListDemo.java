package com.te.collection;

import java.util.Iterator;

public class ArrayListDemo implements Iterable {
	private Object[] array;
	private int position;

	public ArrayListDemo(int arraySize) {
		this.array = new Object[arraySize];
	}

	public void add(Object e) {
		if (this.position >= this.array.length) {
			growArray();
		}
		array[position++] = e;
	}
	

	public Object get(int index) {
		return this.array[index];  

	} 

	public int size() {
		return this.position;
	}

	private void growArray() {
		Object[] temp = new Object[this.array.length + 5];
		System.arraycopy(this.array, 0, temp, 0, this.array.length);
		array = temp;
	}

	@Override
	public String toString() {
		if (size()==0){
		return "[]";
	}
		String string = "[" + array[0];
		for (int i = 1; i < size(); i++) {
			string += " ," + array[i];
		}
		string += "]";
		return string;
}	
	@Override
	public Iterator iterator() {
		
		return new Myitr();
	}
	private class Myitr implements Iterator {
		private int currentIndex = 0;
		
		@Override
		public boolean hasNext() {
			
			return currentIndex < size() && array[currentIndex] != null;
		}

	
		


		@Override
		public Object next() {
			
			return array[currentIndex++];
		}
	}


	public static void main(String[] args) {
		ArrayListDemo arrayListDemo = new ArrayListDemo(10);
		arrayListDemo.add(10);
		arrayListDemo.add(20);
		arrayListDemo.add(30);
		arrayListDemo.get(2);
		arrayListDemo.size();
		System.out.println(arrayListDemo.toString() );
		
		System.out.println("--------------");
		Iterator iterator = arrayListDemo.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			
		}
		
	for (Object object : arrayListDemo) {
		System.out.println(object);
	}
		System.out.println("----------------");
		for (int i = 0; i < arrayListDemo.size(); i++) {
			System.out.println(arrayListDemo.get(i));
		}
	}
}
	

	


