	private static void createColumn(Table t, String string) {
		final TableColumn column = new TableColumn(t, SWT.NONE);
		column.setWidth(100);
		column.setText(string);
	}

