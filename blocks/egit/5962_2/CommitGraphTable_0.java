		final TableColumn commitId = new TableColumn(rawTable, SWT.NONE);
		commitId.setResizable(true);
		commitId.setText(UIText.CommitGraphTable_CommitId);
		int minWidth;
		GC gc = new GC(rawTable.getDisplay());
		try {
			gc.setFont(rawTable.getFont());
			minWidth = gc.stringExtent("0000000").x + 5; //$NON-NLS-1$
		} finally {
			gc.dispose();
		}
		layout.addColumnData(new ColumnWeightData(3, minWidth, true));

