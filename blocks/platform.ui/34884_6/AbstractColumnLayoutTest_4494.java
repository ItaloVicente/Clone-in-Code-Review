
package org.eclipse.jface.tests.layout;

import junit.framework.TestCase;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public final class AbstractColumnLayoutTest extends TestCase {
	Display display;
	Shell shell;

	@Override
	protected void setUp() throws Exception {
		display = Display.getCurrent();
		if (display == null) {
			display = new Display();
		}
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setSize(500, 500);
		shell.setLayout(new FillLayout());

	}

	@Override
	protected void tearDown() throws Exception {
		shell.dispose();
	}

	public void testIgnoreMinimumSize() {
		Composite composite = new Composite(shell, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);

		Table table = new Table(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		TableColumn col3 = new TableColumn(table, SWT.LEFT);
		TableColumn col4 = new TableColumn(table, SWT.LEFT);

		layout.setColumnData(col1, new ColumnWeightData(2, 100));
		layout.setColumnData(col2, new ColumnWeightData(2));
		layout.setColumnData(col3, new ColumnWeightData(1, 30));
		layout.setColumnData(col4, new ColumnPixelData(1));
		

		composite.layout(true, true);
		shell.open();

		assertTrue(col1.getWidth() > 100);
		assertTrue(col1.getWidth() == col2.getWidth());
		assertTrue(Math.abs(col1.getWidth() - 2 * col3.getWidth()) <= 1);
	}

	public void testRecalculatePreferredSize() {
		Composite composite = new Composite(shell, SWT.NONE);
		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);

		Table table = new Table(composite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		TableColumn col3 = new TableColumn(table, SWT.LEFT);
		TableColumn col4 = new TableColumn(table, SWT.LEFT);

		layout.setColumnData(col1, new ColumnWeightData(4,40));
		layout.setColumnData(col2, new ColumnWeightData(1,200));
		layout.setColumnData(col3, new ColumnWeightData(2,30));
		layout.setColumnData(col4, new ColumnPixelData(1));
		

		composite.layout(true, true);
		shell.open();

		assertTrue(col1.getWidth() >= 40);
		assertTrue(col2.getWidth() >= 200);
		assertTrue(col3.getWidth() >= 30);
		assertTrue(Math.abs(col1.getWidth() - 2 * col3.getWidth()) <= 1);
	}
}
