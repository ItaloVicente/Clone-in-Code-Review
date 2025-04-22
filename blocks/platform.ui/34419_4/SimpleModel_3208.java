package org.eclipse.jface.examples.databinding.model;

import java.util.LinkedList;


public class SimpleModel {
	public SimpleModel() {
		personList.add(new SimplePerson("John", "1234", "Wheaton", "IL"));
		personList.add(new SimplePerson("Jane", "1234", "Glen Ellyn", "IL"));
		personList.add(new SimplePerson("Frank", "1234", "Lombard", "IL"));
		personList.add(new SimplePerson("Joe", "1234", "Elmhurst", "IL"));
		personList.add(new SimplePerson("Chet", "1234", "Oak Lawn", "IL"));
		personList.add(new SimplePerson("Wilbur", "1234", "Austin", "IL"));
		personList.add(new SimplePerson("Elmo", "1234", "Chicago", "IL"));
	}


	LinkedList personList = new LinkedList();

	public LinkedList getPersonList() {
		return personList;
	}

}
