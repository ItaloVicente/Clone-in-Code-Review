		newButton = new Button(composite, SWT.PUSH);
		newButton.setText(this.newButtonLabel);
		setButtonLayoutData(newButton);
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewWorkingSet(newButton.getShell());
			}
		});

