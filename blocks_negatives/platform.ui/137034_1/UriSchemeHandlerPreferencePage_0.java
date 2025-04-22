		Composite tableComposite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 0;
		tableComposite.setLayoutData(gridData);
		Table schemeTable = new Table(tableComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		schemeTable.setHeaderVisible(true);
		schemeTable.setLinesVisible(true);
		schemeTable.setFont(parent.getFont());

		TableColumnLayout tableColumnlayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnlayout);

		TableColumn nameColumn = new TableColumn(schemeTable, SWT.NONE, 0);
		nameColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeName);

		TableColumn descriptionColumn = new TableColumn(schemeTable, SWT.NONE, 1);
		descriptionColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeDescription);

		TableColumn appColumn = new TableColumn(schemeTable, SWT.NONE, 2);
		appColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_Handler);

		tableColumnlayout.setColumnData(nameColumn, new ColumnWeightData(20));
		tableColumnlayout.setColumnData(descriptionColumn, new ColumnWeightData(60));
		tableColumnlayout.setColumnData(appColumn, new ColumnWeightData(20));
