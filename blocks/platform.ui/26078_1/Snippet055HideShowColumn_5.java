
	private final class MyEditingSupport extends EditingSupport {
		private final TreeViewer v;
		private final TextCellEditor textCellEditor;

		private MyEditingSupport(ColumnViewer viewer, TreeViewer v,
				TextCellEditor textCellEditor) {
			super(viewer);
			this.v = v;
			this.textCellEditor = textCellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return textCellEditor;
		}

		@Override
		protected Object getValue(Object element) {
			return ((MyModel) element).counter + "";
		}

		@Override
		protected void setValue(Object element, Object value) {
			((MyModel) element).counter = Integer.parseInt(value.toString());
			v.update(element, null);
		}
	}

	class MyContentProvider implements ITreeContentProvider {

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

	class MyColumnLabelProvider extends ColumnLabelProvider {

		private String prefix;

		public MyColumnLabelProvider(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public String getText(Object element) {
			return prefix + " => " + element.toString();
		}

	}

