		GridDataFactory gridDataFactory = GridDataFactory.fillDefaults().grab(true, true);
		gridDataFactory.span(2, 1).indent(0, SWT.DEFAULT);

		TableColumnLayout tableColumnLayout = new TableColumnLayout();

		Composite tableComposite = WidgetFactory.composite(SWT.NONE).layoutData(gridDataFactory::create)
				.layout(tableColumnLayout).create(parent);

		Table schemeTable = WidgetFactory
				.table(SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK)
				.headerVisible(true).linesVisible(true).font(parent.getFont()).create(tableComposite);

		TableColumnFactory columnFactory = WidgetFactory.tableColumn(SWT.NONE);

		TableColumn nameColumn = columnFactory.text(UrlHandlerPreferencePage_ColumnName_SchemeName).create(schemeTable);
		TableColumn descriptionColumn = columnFactory.text(UrlHandlerPreferencePage_ColumnName_SchemeDescription)
				.create(schemeTable);
		TableColumn appColumn = columnFactory.text(UrlHandlerPreferencePage_ColumnName_Handler).create(schemeTable);

		tableColumnLayout.setColumnData(nameColumn, new ColumnWeightData(20));
		tableColumnLayout.setColumnData(descriptionColumn, new ColumnWeightData(60));
		tableColumnLayout.setColumnData(appColumn, new ColumnWeightData(20));
