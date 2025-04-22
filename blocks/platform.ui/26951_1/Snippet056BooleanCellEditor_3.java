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

	private final class MyColumnLabelProvider extends ColumnLabelProvider {
		private String prefix;

		public MyColumnLabelProvider(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public String getText(Object element) {
			return this.prefix + " => " + element.toString();
		}
	}

