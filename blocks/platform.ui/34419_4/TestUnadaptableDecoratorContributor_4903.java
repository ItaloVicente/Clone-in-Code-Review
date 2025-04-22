package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestTreeContentProvider implements ITreeContentProvider {


	@Override
	public Object[] getChildren(Object parentElement) {
		TreeElement parent = (TreeElement) parentElement;
		TreeElement[] children = new TreeElement[10];
		for (int i = 0; i < 10; i++) {
			children[i] = new TreeElement(parent, i);
		}
		return children;

	}

	@Override
	public Object getParent(Object element) {
		return ((TreeElement) element).parent;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		TreeElement root = new TreeElement(null, 0);
		return new Object[] { root };
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {


	}

}
