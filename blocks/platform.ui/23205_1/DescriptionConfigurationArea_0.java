		
		GridData regexGridData = new GridData();
		regexGridData.horizontalSpan = 3;
		regexCheckbox = new Button(descriptionComposite, SWT.CHECK);
		regexCheckbox.setText(MarkerMessages.filtersDialog_regexLabel);
		regexCheckbox.setLayoutData(regexGridData);
