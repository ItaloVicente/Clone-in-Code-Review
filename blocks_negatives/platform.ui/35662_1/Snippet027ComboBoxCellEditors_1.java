	private class MyCellModifier implements ICellModifier {

		private TableViewer viewer;

		public MyCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public boolean canModify(Object element, String property) {
			return true;
		}

		@Override
		public Object getValue(Object element, String property) {
			return new Integer(((MyModel) element).counter / 10);
		}

		@Override
		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			((MyModel) item.getData()).counter = ((Integer) value).intValue() * 10;
			viewer.update(item.getData(), null);
		}
	}

