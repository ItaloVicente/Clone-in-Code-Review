		andButton = new Button(parent, SWT.RADIO);
		andButton.setText(MarkerMessages.AND_Title);
		andButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				andFilters = true;
			}
		});
		GridData andData = new GridData();
		andData.horizontalIndent = 20;
		andButton.setLayoutData(andData);

		orButton = new Button(parent, SWT.RADIO);
		orButton.setText(MarkerMessages.OR_Title);
		orButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				andFilters = false;
			}
		});
		GridData orData = new GridData();
		orData.horizontalIndent = 20;
		orButton.setLayoutData(orData);
