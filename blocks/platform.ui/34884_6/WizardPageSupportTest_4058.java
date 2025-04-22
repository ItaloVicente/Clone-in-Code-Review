
package org.eclipse.jface.tests.databinding.viewers;

import org.eclipse.core.databinding.observable.IDecoratingObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.property.IPropertyObservable;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.internal.databinding.viewers.ViewerInputProperty;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ViewersObservablesTest extends AbstractDefaultRealmTestCase {
	TableViewer viewer;
	Realm realm;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		realm = SWTObservables.getRealm(Display.getCurrent());
		Shell shell = new Shell();
		viewer = new TableViewer(shell, SWT.NONE);
	}

	@Override
	protected void tearDown() throws Exception {
		Shell shell = viewer.getTable().getShell();
		if (!shell.isDisposed())
			shell.dispose();
		shell = null;
		realm = null;
		super.tearDown();
	}

	public void testObserveInput_InstanceOfViewerInputObservableValue() {
		IViewerObservableValue observable = (IViewerObservableValue) ViewersObservables
				.observeInput(viewer);
		assertTrue(observable.getViewer() == viewer);
		IPropertyObservable propertyObservable = (IPropertyObservable) ((IDecoratingObservable) observable)
				.getDecorated();
		assertTrue(propertyObservable.getProperty() instanceof ViewerInputProperty);
	}
}
