	private TreeViewerColumn createColumnFor(final TreeViewer v, String label,
			TextCellEditor textCellEditor) {
		TreeViewerColumn column = new TreeViewerColumn(v, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText(label);
		column.setLabelProvider(new MyColumnLabelProvider(label));
		column.setEditingSupport(new MyEditingSupport(v, v, textCellEditor));
		return column;
	}

	private Button createButtonFor(final Shell shell, String label) {
		Button b = new Button(shell, SWT.PUSH);
		b.setText(label);
		b.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return b;
	}

	public class MyModel {

		public MyModel parent;
		public ArrayList<MyModel> child = new ArrayList<MyModel>();
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
