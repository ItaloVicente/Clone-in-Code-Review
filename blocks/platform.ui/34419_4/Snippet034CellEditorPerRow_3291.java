
package org.eclipse.jface.snippets.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class Snippet031TableViewerCustomTooltipsMultiSelection {
	public class MyLableProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof MyModel) {
				switch (columnIndex) {
					case 0: return ((MyModel)element).col1;
					case 1: return ((MyModel)element).col2;
				}
			}
			return "";
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

	}

	public class MyModel {
		public String col1, col2;

		public MyModel(String c1, String c2) {
			col1 = c1;
			col2 = c2;
		}

		@Override
		public String toString() {
			return col1 + col2;
		}

	}

	public Snippet031TableViewerCustomTooltipsMultiSelection(Shell shell) {
		final Table table = new Table(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
        table.setLinesVisible(true);

		final TableViewer v = new TableViewer(table);
		TableColumn tableColumn1 = new TableColumn(table, SWT.NONE);
		TableColumn tableColumn2 = new TableColumn(table, SWT.NONE);

		String column1 = "Column 1", column2 = "Column 2";
        tableColumn1.setText(column1);
        tableColumn2.setText(column2);
        tableColumn1.pack();
        tableColumn2.pack();

        v.setColumnProperties(new String[] { column1, column2 });
		v.setLabelProvider(new MyLableProvider());
		v.setContentProvider(new ArrayContentProvider());
		v.setInput(createModel());

	    Listener tableListener = new Listener () {
	    	Shell tooltip = null;
	    	Label label = null;

	    	@Override
			public void handleEvent (Event event) {
			   switch (event.type) {
				   	case SWT.KeyDown:
				   	case SWT.Dispose:
				   	case SWT.MouseMove: {
				   		if (tooltip == null) break;
				   		tooltip.dispose ();
				   		tooltip = null;
				   		label = null;
				   		break;
				   	}
				   	case SWT.MouseHover: {
				   		Point coords = new Point(event.x, event.y);
						TableItem item = table.getItem(coords);
				   		if (item != null) {
				   			int columnCount = table.getColumnCount();
							for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				   				if (item.getBounds(columnIndex).contains(coords)) {
				   					if (tooltip != null  && !tooltip.isDisposed ()) tooltip.dispose ();

				   					tooltip = new Shell (table.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
				   					tooltip.setBackground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));
				   					FillLayout layout = new FillLayout ();
				   					layout.marginWidth = 2;
				   					tooltip.setLayout (layout);
				   					label = new Label (tooltip, SWT.NONE);
				   					label.setForeground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_FOREGROUND));
				   					label.setBackground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));

				   					label.setData ("_TableItem_", item);

			   						label.setText("Tooltip: " + item.getData() + " : " + columnIndex);

				   					label.addListener (SWT.MouseExit, tooltipLabelListener);
				   					label.addListener (SWT.MouseDown, tooltipLabelListener);

				   					Point size = tooltip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
				   					Rectangle rect = item.getBounds (columnIndex);
				   					Point pt = table.toDisplay (rect.x, rect.y);
				   					tooltip.setBounds (pt.x, pt.y, size.x, size.y);

				   					tooltip.setVisible (true);
				   					break;
				   				}
				   			}
				   		}
				   	}
			   }
	    	}
		};

		table.addListener (SWT.Dispose, tableListener);
		table.addListener (SWT.KeyDown, tableListener);
		table.addListener (SWT.MouseMove, tableListener);
		table.addListener (SWT.MouseHover, tableListener);
	}

	    final TooltipLabelListener tooltipLabelListener = new TooltipLabelListener();
	    final class TooltipLabelListener implements Listener {
	        private boolean isCTRLDown(Event e) {
	        	return (e.stateMask & SWT.CTRL) != 0;
	        }

		@Override
		public void handleEvent (Event event) {
			   Label label = (Label)event.widget;
			   Shell shell = label.getShell ();
			   switch (event.type) {
				   	case SWT.MouseDown:	/* Handle a user Click */
				   		Event e = new Event ();
				   		e.item = (TableItem) label.getData ("_TableItem_");
				   		Table table = ((TableItem) e.item).getParent();

				   		TableItem [] newSelection = null;
				   		if (isCTRLDown(event)) {
				   			TableItem[] sel = table.getSelection();
				   			for (int i = 0; i < sel.length; ++i) {
				   				if (e.item.equals(sel[i])) {
				   					newSelection = new TableItem[sel.length - 1];
				   					System.arraycopy(sel, 0, newSelection, 0, i);
				   					System.arraycopy(sel, i+1, newSelection, i, sel.length - i - 1);
				   					break;
				   				}
		   					}

				   			if (newSelection == null) {
				   				newSelection = new TableItem[sel.length + 1];
				   				System.arraycopy(sel, 0, newSelection, 0, sel.length);
				   				newSelection[sel.length] = (TableItem) e.item;
				   			}

				   		} else {
				   			newSelection = new TableItem[] { (TableItem) e.item };
				   		}
				   		table.setSelection (newSelection);
				   		table.notifyListeners (SWT.Selection, e);

				   		shell.dispose ();
				   		table.setFocus();
				   		break;
				   	case SWT.MouseExit:
				   		shell.dispose ();
				   		break;
			   }
	    }};



	private List<MyModel> createModel() {
		List<MyModel> list = new ArrayList<MyModel>();
		list.add(new MyModel("A", "B"));
		list.add(new MyModel("C", "D"));
		list.add(new MyModel("E", "F"));
		return list;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Snippet031TableViewerCustomTooltipsMultiSelection(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}
