	private void layout(Table rawTable, TableColumnLayout layout) {
		rawTable.getParent().layout();
		int tableWidth = rawTable.getSize().x;
		int columnsWidth = 0;
		TableColumn[] columns = rawTable.getColumns();
		for (TableColumn col : columns) {
			columnsWidth += col.getWidth();
		}
		if (columnsWidth > tableWidth) {
			layout.setColumnData(columns[1],
					new ColumnWeightData(20, 200, true));
			for (int i = 2; i < columns.length; i++) {
				layout.setColumnData(columns[i],
						new ColumnWeightData(5, 80, true));
			}
			rawTable.getParent().layout();
		}
		rawTable.redraw();
	}

