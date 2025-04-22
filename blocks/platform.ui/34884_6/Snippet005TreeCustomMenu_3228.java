
package org.eclipse.jface.snippets.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet004HideSelection {

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

	public Snippet004HideSelection(Shell shell) {
		final TableViewer v = new TableViewer(shell, SWT.BORDER
				| SWT.FULL_SELECTION);
		v.setLabelProvider(new LabelProvider());
		v.setContentProvider(ArrayContentProvider.getInstance());
		v.setInput(createModel());
		v.getTable().setLinesVisible(true);
		v.getTable().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if (v.getTable().getItem(new Point(e.x, e.y)) == null) {
					v.setSelection(new StructuredSelection());
				}
			}

		});
	}

	private List<MyModel> createModel() {
		List<MyModel> elements = new ArrayList<MyModel>();

		for (int i = 0; i < 10; i++) {
			elements.add(new MyModel(i));
		}
		return elements;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Snippet004HideSelection(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

}
