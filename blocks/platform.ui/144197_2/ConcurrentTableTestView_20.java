		updateCount = new Label(buttonBar, SWT.NONE);
		updateCount.setLayoutData(new GridData(GridData.FILL_BOTH));

		slowComparisons = new Button(buttonBar, SWT.CHECK);
		slowComparisons.setLayoutData(new GridData(GridData.FILL_BOTH));
		slowComparisons.setText("Slow comparisons");
		slowComparisons.setSelection(enableSlowComparisons);
		slowComparisons.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableSlowComparisons = slowComparisons.getSelection();
				super.widgetSelected(e);
			}
		});

		final Button limitSize = new Button(buttonBar, SWT.CHECK);
		limitSize.setLayoutData(new GridData(GridData.FILL_BOTH));
		limitSize.setText("Limit table size to 400");
		limitSize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (limitSize.getSelection()) {
					contentProvider.setLimit(400);
				} else {
					contentProvider.setLimit(-1);
