
package org.eclipse.jface.snippets.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

public class Snippet009CellEditors {
	public class MyModel {
		public int counter;

		public MyModel(int counter) {
			this.counter = counter;
		}

		@Override
		public String toString() {
			return "Item " + this.counter;
		}
	}

	public Snippet009CellEditors(Shell shell) {
		final TableViewer v = new TableViewer(shell,SWT.BORDER|SWT.FULL_SELECTION);
		v.setLabelProvider(new LabelProvider());
		v.setContentProvider(ArrayContentProvider.getInstance());
		v.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return ((MyModel)element).counter % 2 == 0;
			}

			@Override
			public Object getValue(Object element, String property) {
				return ((MyModel)element).counter + "";
			}

			@Override
			public void modify(Object element, String property, Object value) {
				TableItem item = (TableItem)element;
				((MyModel)item.getData()).counter = Integer.parseInt(value.toString());
				v.update(item.getData(), null);
			}

		});
		v.setColumnProperties(new String[] { "column1" });
		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()) });


		MyModel[] model = createModel();
		v.setInput(model);
		v.getTable().setLinesVisible(true);
	}

	private MyModel[] createModel() {
		MyModel[] elements = new MyModel[10];

		for( int i = 0; i < 10; i++ ) {
			elements[i] = new MyModel(i);
		}

		return elements;
	}

	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Snippet009CellEditors(shell);
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}

		display.dispose ();

	}

}
