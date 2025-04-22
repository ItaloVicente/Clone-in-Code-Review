package org.eclipse.ui.tests.decorators;

public class TableElement extends TestElement{

	int index;

	public TableElement(int newIndex) {
		super();
		index = newIndex;
		name = "Table Item " + String.valueOf(index);

	}
}
