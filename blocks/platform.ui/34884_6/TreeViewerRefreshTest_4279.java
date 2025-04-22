
package org.eclipse.jface.tests.performance;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Shell;

public abstract class TreeTest extends ViewerTest {
	


	TreeViewer viewer;

	public TreeTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public TreeTest(String testName) {
		super(testName);
	}


	protected StructuredViewer createViewer(Shell shell) {
		viewer = createTreeViewer(shell);
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setUseHashlookup(true);
		return viewer;
	}

	protected TreeViewer createTreeViewer(Shell shell) {
		return new TreeViewer(shell);
	}

	protected Object getInitialInput() {
		return new TestTreeElement(0, null);
	}

	private IContentProvider getContentProvider() {
		return new ITreeContentProvider() {
	
			public Object[] getChildren(Object parentElement) {
				TestTreeElement element = (TestTreeElement) parentElement;
				return element.children;
			}
	
			public Object getParent(Object element) {
				return ((TestTreeElement) element).parent;
			}
	
			public boolean hasChildren(Object element) {
				return ((TestTreeElement) element).children.length > 0;
			}
	
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}
	
			public void dispose() {
			}
	
			public void inputChanged(Viewer localViewer, Object oldInput,
					Object newInput) {
			}
	
		};
	}

}
