
package org.eclipse.jface.snippets.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class Snippet038TreeViewerInlinelEditingOldAPI {
	private class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((MyModel) inputElement).child.toArray();
		}

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element == null) {
				return null;
			}

			return ((MyModel) element).parent;
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((MyModel) element).child.size() > 0;
		}

	}

	public class MyModel {
		public MyModel parent;
		public List<MyModel> child = new ArrayList<MyModel>();
		public int counter;

		public MyModel(int counter, MyModel parent) {
			this.parent = parent;
			this.counter = counter;
		}

		@Override
		public String toString() {
			String rv = "Item ";
			if (parent != null) {
				rv = parent.toString() + ".";
			}
			rv += counter;

			return rv;
		}
	}

	public class MyLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		FontRegistry registry = new FontRegistry();

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return "Column " + columnIndex + " => " + element.toString();
		}
	}

	public Snippet038TreeViewerInlinelEditingOldAPI(Shell shell) {
		final TreeViewer viewer = new TreeViewer(shell, SWT.FULL_SELECTION);

		createColumnFor(viewer, "Column 1");
		createColumnFor(viewer, "Column 2");

		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTree()),
				new TextCellEditor(viewer.getTree()) });

		viewer.setColumnProperties(new String[] { "col1", "col2" });
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return ((MyModel) element).counter + "";
			}

			@Override
			public void modify(Object element, String property, Object value) {
				((MyModel) ((TreeItem) element).getData()).counter = Integer
						.parseInt(value.toString());
				viewer.update(((TreeItem) element).getData(), null);
			}

		});
		viewer.setLabelProvider(new MyLabelProvider());
		viewer.setContentProvider(new MyContentProvider());
		viewer.setInput(createModel());
	}

	private void createColumnFor(TreeViewer viewer, String label) {
		TreeColumn column = new TreeColumn(viewer.getTree(), SWT.NONE);
		column.setWidth(200);
		column.setText(label);
	}

	private MyModel createModel() {
		MyModel root = new MyModel(0, null);
		root.counter = 0;

		MyModel tmp;
		for (int i = 1; i < 10; i++) {
			tmp = new MyModel(i, root);
			root.child.add(tmp);
			for (int j = 1; j < i; j++) {
				tmp.child.add(new MyModel(j, tmp));
			}
		}
		return root;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Snippet038TreeViewerInlinelEditingOldAPI(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}
