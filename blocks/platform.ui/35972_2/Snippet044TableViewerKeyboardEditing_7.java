	private class MyColumnLabelProvider extends ColumnLabelProvider {

		private int columnIndex;
		private Table table;

		public MyColumnLabelProvider(Table table, int columnIndex) {
			this.table = table;
			this.columnIndex = columnIndex;
		}
