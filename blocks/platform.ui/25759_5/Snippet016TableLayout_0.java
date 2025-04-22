	private TableColumn createTableColumn(Table table, String textColumn) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(textColumn);
		column.setMoveable(true);
		return column;
	}

