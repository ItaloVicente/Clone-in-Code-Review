		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return "Column " + property + " => " + element.toString();
		}

		@Override
		protected void setValue(Object element, Object value) {

		}

	}

	private class MyColumnLabelProvider extends ColumnLabelProvider {
		FontRegistry registry = JFaceResources.getFontRegistry();
		private String columnIndex;

		public MyColumnLabelProvider(String columnIndex) {
			this.columnIndex = columnIndex;
