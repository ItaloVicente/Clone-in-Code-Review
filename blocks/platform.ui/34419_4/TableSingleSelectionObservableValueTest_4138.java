
package org.eclipse.jface.tests.internal.databinding.swt;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableObservableValueTest extends AbstractSWTTestCase {
	public void testDispose() throws Exception {
		Table table = new Table(getShell(), SWT.NONE);
		IObservableValue observableValue = SWTObservables
				.observeSingleSelectionIndex(table);

		TableItem item1 = new TableItem(table, SWT.NONE);
		item1.setText("Item1");
		TableItem item2 = new TableItem(table, SWT.NONE);
		item2.setText("Item2");

		assertEquals(-1, table.getSelectionIndex());
		assertEquals(-1, ((Integer) observableValue.getValue()).intValue());

		table.select(0);
		table.notifyListeners(SWT.Selection, null);

		assertEquals(0, table.getSelectionIndex());
		assertEquals(new Integer(0), observableValue.getValue());

		observableValue.dispose();

		table.select(1);
		table.notifyListeners(SWT.Selection, null);
		assertEquals(1, table.getSelectionIndex());
	}
}
