package org.eclipse.jface.tests.performance;


public class TestTreeElement extends TestElement {

	TestTreeElement parent;

	TestTreeElement[] children = new TestTreeElement[0];

	private static int index = 0;

	static String characters = "M1NqBwV2CeXrZ3LtKyJ4HuGiF5DoSpA6PaOsI7UdYfT8RgEhW9Qjk0DlWzMxUcsvfbwnm";

	public TestTreeElement(int index, TestTreeElement treeParent) {
		super();
		this.parent = treeParent;
		name = generateFirstEntry() + String.valueOf(index);
	}

	static String generateFirstEntry() {

		String next = characters.substring(index);
		index++;
		if (index > characters.length() - 2)
			index = 0;
		return next;
	}

	public void createChildren(int count) {
		children = new TestTreeElement[count];
		for (int i = 0; i < count; i++) {
			children[i] = new TestTreeElement(i, this);
		}
	}

}
