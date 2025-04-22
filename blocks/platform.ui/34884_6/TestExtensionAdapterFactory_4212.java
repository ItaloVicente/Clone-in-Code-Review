
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestEmptyContentProvider implements ITreeContentProvider {

	private static final Object[] NO_CHILDREN = new Object[0];

	public static boolean _throw;
	
	public static void resetTest() {
		_throw = false;
	}

	public Object[] getChildren(Object parentElement) {
		if (_throw)
			throw new RuntimeException("Throwing...");
		return NO_CHILDREN;
	}

	public Object getParent(Object element) {
		if (_throw)
			throw new RuntimeException("Throwing...");
		return null;
	}

	public boolean hasChildren(Object element) {
		if (_throw)
			throw new RuntimeException("Throwing...");
		return false;
	}

	public Object[] getElements(Object inputElement) {
		if (_throw)
			throw new RuntimeException("Throwing...");
		return NO_CHILDREN;
	}

	public void dispose() {}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

}
