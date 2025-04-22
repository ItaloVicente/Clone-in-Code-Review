
package org.eclipse.jface.snippets.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet011CustomTooltips {
	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		TableViewer v = new TableViewer(shell, SWT.FULL_SELECTION);
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);
		v.setContentProvider(ArrayContentProvider.getInstance());
		ColumnViewerToolTipSupport.enableFor(v,ToolTip.NO_RECREATE);

		CellLabelProvider labelProvider = new CellLabelProvider() {

			@Override
			public String getToolTipText(Object element) {
				return "Tooltip (" + element + ")";
			}

			@Override
			public Point getToolTipShift(Object object) {
				return new Point(5, 5);
			}

			@Override
			public int getToolTipDisplayDelayTime(Object object) {
				return 2000;
			}

			@Override
			public int getToolTipTimeDisplayed(Object object) {
				return 5000;
			}

			@Override
			public void update(ViewerCell cell) {
				cell.setText(cell.getElement().toString());

			}
		};

		TableViewerColumn column = new TableViewerColumn(v, SWT.NONE);
		column.setLabelProvider(labelProvider);
		column.getColumn().setText("Column 1");
		column.getColumn().setWidth(100);
		String[] values = new String[] { "one", "two", "three", "four", "five", "six",
				"seven", "eight", "nine", "ten" };
		v.setInput(values);

		shell.setSize(200, 200);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

}
