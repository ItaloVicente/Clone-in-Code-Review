		public String getText(Object element) {
			return "Column " + table.getColumnOrder()[columnIndex] + " => " + element.toString();
		}
	}

	private class MyEditingSupport extends EditingSupport {

		private String property;

		public MyEditingSupport(ColumnViewer viewer, String property) {
			super(viewer);
			this.property = property;
