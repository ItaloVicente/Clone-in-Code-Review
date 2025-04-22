package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestTableContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(Object inputElement) {
		TableElement[] elements = new TableElement[100];

		for (int i = 0; i < elements.length; i++) {
			elements[i] = new TableElement(i);
		}

		return elements;
	}
	@Override
	public void dispose() {

	}
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}
