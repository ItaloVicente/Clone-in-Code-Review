
package org.eclipse.jface.tests.internal.databinding.swt;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.ObservableDelegateTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.swt.SWTMutableObservableValueContractTest;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerObservableValueSelectionTest extends ObservableDelegateTest {
	private Delegate delegate;

	private Spinner spinner;

	private IObservableValue observable;

	public SpinnerObservableValueSelectionTest() {
		this(null);
	}

	public SpinnerObservableValueSelectionTest(String testName) {
		super(testName, new Delegate());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		delegate = (Delegate) getObservableContractDelegate();
		observable = (IObservableValue) getObservable();
		spinner = delegate.spinner;
	}

	@Override
	protected IObservable doCreateObservable() {
		return getObservableContractDelegate().createObservable(
				DisplayRealm.getRealm(Display.getDefault()));
	}

	public void testGetValue() throws Exception {
		int value = 100;
		spinner.setSelection(value);
		assertEquals(new Integer(value), observable.getValue());
	}

	public void testSetValue() throws Exception {
		int value = 100;
		observable.setValue(new Integer(value));
		assertEquals(value, spinner.getSelection());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(
				SpinnerObservableValueSelectionTest.class.toString());
		suite.addTestSuite(SpinnerObservableValueSelectionTest.class);
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
	}

			AbstractObservableValueContractDelegate {
		private Shell shell;

		Spinner spinner;

		@Override
		public void setUp() {
			shell = new Shell();
			spinner = new Spinner(shell, SWT.NONE);
			spinner.setMaximum(1000);
		}

		@Override
		public void tearDown() {
			shell.dispose();
		}

		@Override
		public IObservableValue createObservableValue(Realm realm) {
			return WidgetProperties.selection().observe(realm, spinner);
		}

		@Override
		public void change(IObservable observable) {
			spinner.setSelection(createIntegerValue(
					(IObservableValue) observable).intValue());
			spinner.notifyListeners(SWT.Selection, null);
		}

		@Override
		public Object getValueType(IObservableValue observable) {
			return Integer.TYPE;
		}

		@Override
		public Object createValue(IObservableValue observable) {
			return createIntegerValue(observable);
		}

		private Integer createIntegerValue(IObservableValue observable) {
			return new Integer(((Integer) observable.getValue()).intValue() + 1);
		}
	}
}
