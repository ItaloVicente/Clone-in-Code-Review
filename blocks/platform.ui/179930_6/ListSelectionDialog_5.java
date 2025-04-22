		if (optionalCheckboxLabel != null) {
			optionalCheckbox = new Button(composite, SWT.CHECK);
			optionalCheckbox.setSelection(optionalCheckboxValue);
			optionalCheckbox.addSelectionListener(
					widgetSelectedAdapter(e -> optionalCheckboxValue = optionalCheckbox.getSelection()));
			GridData gd = new GridData();
			gd.horizontalAlignment = SWT.BEGINNING;
			optionalCheckbox.setLayoutData(gd);
			optionalCheckbox.setText(optionalCheckboxLabel);
		}
