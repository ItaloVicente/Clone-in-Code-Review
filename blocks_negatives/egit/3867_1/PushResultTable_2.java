		tablePanel = new Composite(parent, SWT.NONE);
		tablePanel.setLayout(new GridLayout());
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = TABLE_PREFERRED_HEIGHT;
		layoutData.widthHint = TABLE_PREFERRED_WIDTH;
		tableViewer = new TableViewer(tablePanel);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		final Table table = tableViewer.getTable();
