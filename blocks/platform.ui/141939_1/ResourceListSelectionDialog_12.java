				okPressed();
			}
		});

		if (getAllowUserToToggleDerived()) {
			showDerivedButton = new Button(dialogArea, SWT.CHECK);
			showDerivedButton.setText(IDEWorkbenchMessages.ResourceSelectionDialog_showDerived);
			showDerivedButton.addSelectionListener(new SelectionAdapter() {
				@Override
