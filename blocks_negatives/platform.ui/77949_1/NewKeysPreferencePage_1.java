		conflictViewer = new TableViewer(rightDataArea, SWT.SINGLE | SWT.V_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION);
		Table table = conflictViewer.getTable();
		table.setHeaderVisible(true);
		TableColumn bindingNameColumn = new TableColumn(table, SWT.LEAD);
		bindingNameColumn.setText(NewKeysPreferenceMessages.CommandNameColumn_Text);
		bindingNameColumn.setWidth(150);
		TableColumn bindingContextNameColumn = new TableColumn(table, SWT.LEAD);
		bindingContextNameColumn.setText(NewKeysPreferenceMessages.WhenColumn_Text);
		bindingContextNameColumn.setWidth(150);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gridData);
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(60));
		tableLayout.addColumnData(new ColumnWeightData(40));
		table.setLayout(tableLayout);
		conflictViewer.setContentProvider(new IStructuredContentProvider() {
