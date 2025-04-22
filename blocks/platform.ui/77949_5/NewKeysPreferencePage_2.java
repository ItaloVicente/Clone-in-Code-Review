	private TableViewer createBasicConflictViewer(Composite parent, String labelText) {
		final Label descriptionLabel = new Label(parent, SWT.NONE);
		descriptionLabel.setText(labelText);
		descriptionLabel.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		TableViewer viewer = new TableViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		TableColumn bindingNameColumn = new TableColumn(table, SWT.LEAD);
		bindingNameColumn.setText(NewKeysPreferenceMessages.CommandNameColumn_Text);
		bindingNameColumn.setWidth(150);
		TableColumn bindingContextNameColumn = new TableColumn(table, SWT.LEAD);
		bindingContextNameColumn.setText(NewKeysPreferenceMessages.WhenColumn_Text);
		bindingContextNameColumn.setWidth(150);
		table.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 50).create());
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(60));
		tableLayout.addColumnData(new ColumnWeightData(40));
		table.setLayout(tableLayout);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new BindingElementLabelProvider() {
			@Override
			public String getColumnText(Object o, int index) {
				BindingElement element = (BindingElement) o;
				if (index == 0) {
					return element.getName();
				}
				return element.getContext().getName();
			}
		});
		return viewer;
