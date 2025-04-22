		column = createColumnFor(viewer, "Column 2");
		column.setLabelProvider(createCellLabelProvider());

		viewer.setInput(createModel(10));
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		Button b = createButtonFor(shell, "Modify Input");
		b.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setInput(createModel((int) (Math.random() * 10)));
			}

		});

		b = createButtonFor(shell, "Refresh");
		b.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.refresh();
			}

		});
	}

	private CellLabelProvider createCellLabelProvider() {
		return new CellLabelProvider() {
