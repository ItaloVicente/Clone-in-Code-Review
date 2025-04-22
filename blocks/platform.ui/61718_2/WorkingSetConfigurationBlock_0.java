		newButton = new Button(composite, SWT.PUSH);
		newButton.setText(WorkbenchMessages.WorkingSetConfigurationBlock_NewWorkingSet_button);
		setButtonLayoutData(newButton);
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewWorkingSet(newButton.getShell());
			}
		});

