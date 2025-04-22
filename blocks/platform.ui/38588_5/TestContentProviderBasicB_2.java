package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestContentProviderBasicA implements ITreeContentProvider {

	private Object[] children = new Object[] { new TreeContentA("child3"), new TreeContentA("child1") };

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public Object[] getChildren(Object parentElement) {
		return children;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return true;
	}

}

class TreeContentA {
	private String name;

	public TreeContentA(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
