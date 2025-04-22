		if (optionalCheckboxLabel != null) {
			Composite checkboxComposite = new Composite(composite, SWT.NONE);
			checkboxComposite.setLayout(new GridLayout(2, false));
			optionalCheckbox = new Button(checkboxComposite, SWT.CHECK);
			optionalCheckbox.setSelection(optionalCheckboxDefaultValue);
			optionalCheckbox.addSelectionListener(
					widgetSelectedAdapter(e -> optionalCheckboxValue = optionalCheckbox.getSelection()));
			GridData gd = new GridData();
			gd.horizontalAlignment = SWT.BEGINNING;
			optionalCheckbox.setLayoutData(gd);

			Label label = new Label(checkboxComposite, SWT.NONE);
			label.setText(optionalCheckboxLabel);
			gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = SWT.BEGINNING;
		}
