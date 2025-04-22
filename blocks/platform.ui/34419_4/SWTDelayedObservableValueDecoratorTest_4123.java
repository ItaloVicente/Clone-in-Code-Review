
package org.eclipse.jface.tests.internal.databinding.swt;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;

public class ListSingleSelectionObservableValueTest extends AbstractSWTTestCase {
	public void testSetValue() throws Exception {
		List list = new List(getShell(), SWT.NONE);
		IObservableValue observableValue = SWTObservables
				.observeSingleSelectionIndex(list);
		list.add("Item1");

		assertEquals(-1, list.getSelectionIndex());
		assertEquals(-1, ((Integer) observableValue.getValue()).intValue());

		Integer value = new Integer(0);
		observableValue.setValue(value);
		assertEquals("list selection index", value.intValue(), list
				.getSelectionIndex());
		assertEquals("observable value", value, observableValue.getValue());
	}

	public void testDispose() throws Exception {
		List list = new List(getShell(), SWT.NONE);
		IObservableValue observableValue = SWTObservables
				.observeSingleSelectionIndex(list);
		list.add("Item1");
		list.add("Item2");

		assertEquals(-1, list.getSelectionIndex());
		assertEquals(-1, ((Integer) observableValue.getValue()).intValue());

		list.select(0);
		list.notifyListeners(SWT.Selection, null);
		assertEquals(0, list.getSelectionIndex());
		assertEquals(new Integer(0), observableValue.getValue());

		observableValue.dispose();

		list.select(1);
		list.notifyListeners(SWT.Selection, null);
		assertEquals(1, list.getSelectionIndex());
	}
}
