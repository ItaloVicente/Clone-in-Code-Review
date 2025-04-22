	FetchResultTable(final Composite parent) {
		tablePanel = new Composite(parent, SWT.NONE);
		tablePanel.setLayout(new GridLayout());
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = TABLE_PREFERRED_HEIGHT;
		layoutData.widthHint = TABLE_PREFERRED_WIDTH;
		tableViewer = new TableViewer(tablePanel);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		rejectedColor = new Color(parent.getDisplay(), 255, 0, 0);
		updatedColor = new Color(parent.getDisplay(), 0, 255, 0);
		upToDateColor = new Color(parent.getDisplay(), 245, 245, 245);

		tablePanel.addDisposeListener(new DisposeListener() {
