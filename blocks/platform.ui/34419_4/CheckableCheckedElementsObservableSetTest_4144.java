
package org.eclipse.jface.tests.internal.databinding.swt;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextObservableValueTest extends AbstractDefaultRealmTestCase {
	private Text text;
	private ValueChangeEventTracker listener;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Shell shell = new Shell();
		text = new Text(shell, SWT.NONE);

		listener = new ValueChangeEventTracker();
	}

	public void testConstructorUpdateEventTypes() {
		try {
			WidgetProperties.text(SWT.None);
			WidgetProperties.text(SWT.FocusOut);
			WidgetProperties.text(SWT.Modify);
			WidgetProperties.text(SWT.DefaultSelection);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail();
		}

		try {
			WidgetProperties.text(SWT.Verify);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	public void testGetValueBeforeFocusOutChangeEventsFire() throws Exception {
		IObservableValue observableValue = WidgetProperties.text(SWT.FocusOut)
				.observe(Realm.getDefault(), text);
		observableValue.addValueChangeListener(listener);

		String a = "a";
		String b = "b";

		text.setText(a);

		assertEquals(0, listener.count);

		assertEquals(a, observableValue.getValue());
		assertEquals(1, listener.count);

		text.setText(b);

		assertEquals(1, listener.count);

		text.notifyListeners(SWT.FocusOut, null);

		assertEquals(2, listener.count);
		assertEquals(a, listener.event.diff.getOldValue());
		assertEquals(b, listener.event.diff.getNewValue());
	}

	public void testDispose() throws Exception {
		IObservableValue observableValue = WidgetProperties.text(SWT.Modify)
				.observe(Realm.getDefault(), text);
		ValueChangeEventTracker testCounterValueChangeListener = new ValueChangeEventTracker();
		observableValue.addValueChangeListener(testCounterValueChangeListener);

		String expected1 = "Test123";
		text.setText(expected1);

		assertEquals(1, testCounterValueChangeListener.count);
		assertEquals(expected1, text.getText());
		assertEquals(expected1, observableValue.getValue());

		observableValue.dispose();

		String expected2 = "NewValue123";
		text.setText(expected2);

		assertEquals(1, testCounterValueChangeListener.count);
		assertEquals(expected2, text.getText());
	}
}
