
package org.eclipse.jface.tests.performance;

public class TestElement {
	
	String name;

	public TestElement() {
		super();
	}
	
	public TestElement(int index) {
		name = TestTreeElement.generateFirstEntry() + String.valueOf(index);
	}

	public String getText() {
		return name;
	}
}
