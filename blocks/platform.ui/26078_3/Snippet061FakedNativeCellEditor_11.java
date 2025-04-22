
	private final class MyEditingSupport extends EditingSupport {
		private final TextCellEditor textCellEditor;
		private final TreeViewer viewer;

		private MyEditingSupport(ColumnViewer viewer,
				TextCellEditor textCellEditor, TreeViewer viewer2) {
			super(viewer);
			this.textCellEditor = textCellEditor;
			this.viewer = viewer2;
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
			return ((File) element).counter + "";
		}

		@Override
		protected void setValue(Object element, Object value) {
			((File) element).counter = Integer.parseInt(value.toString());
			viewer.update(element, null);
		}
	}

	private class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((File) inputElement).child.toArray();
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
			return ((File) element).parent;
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((File) element).child.size() > 0;
		}

	}

