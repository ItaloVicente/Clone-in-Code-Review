
		final TableColumn committerDate = new TableColumn(rawTable, SWT.NONE);
		committerDate.setResizable(true);
		committerDate.setText(UIText.CommitGraphTable_committerDataColumn);
		committerDate.setWidth(100);
		layout.addColumnData(new ColumnWeightData(5, true));
