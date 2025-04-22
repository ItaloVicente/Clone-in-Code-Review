
package org.eclipse.jface.tests.labelProviders;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.tests.viewers.TestElement;
import org.eclipse.jface.tests.viewers.ViewerTestCase;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;

public abstract class CompositeLabelProviderTest extends ViewerTestCase {

	class LabelTableContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return fRootElement.getChildren();
		}

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

	}

	Color background;
	Color foreground;
	Font font;

	public CompositeLabelProviderTest(String name) {
		super(name);
	}

	void initializeColors(Control parent) {
		background = parent.getDisplay().getSystemColor(SWT.COLOR_RED);
		foreground = parent.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		font = JFaceResources.getBannerFont();
	}

	class TestTreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			return ((TestElement) parentElement).getChildren();
		}

		@Override
		public Object getParent(Object element) {
			return ((TestElement) element).getContainer();
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return fRootElement.getChildren();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}
}
