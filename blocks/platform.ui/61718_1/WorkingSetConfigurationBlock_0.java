		newButton = new Button(composite, SWT.PUSH);
		newButton.setText("New..."); //$NON-NLS-1$
		setButtonLayoutData(newButton);
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewWorkingSet(newButton.getShell());
			}
		});

