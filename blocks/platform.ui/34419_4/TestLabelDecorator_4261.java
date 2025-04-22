
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TestFilterItemsThatEndIn3 extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if(element instanceof TestExtensionTreeData) {
			TestExtensionTreeData data = (TestExtensionTreeData)element;
			return (data.getName() != null && !data.getName().endsWith("3"));
		}
		return true;
	}

}
