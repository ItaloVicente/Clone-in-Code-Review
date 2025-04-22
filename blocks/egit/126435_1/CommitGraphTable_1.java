	private void layout(Table rawTable, TableColumnLayout layout,
			TableColumn[] dynamicColumns) {
		rawTable.getParent().layout();
		int tableWidth = rawTable.getSize().x;
		int columnsWidth = 0;
		for (TableColumn col : rawTable.getColumns()) {
			columnsWidth += col.getWidth();
		}
		if (columnsWidth > tableWidth) {
			layout.setColumnData(dynamicColumns[0],
					new ColumnWeightData(20, 200, true));
			for (int i = 1; i < dynamicColumns.length; i++) {
				layout.setColumnData(dynamicColumns[i],
						new ColumnWeightData(5, 80, true));
			}
			rawTable.getParent().layout();
		}
		rawTable.redraw();
	}

