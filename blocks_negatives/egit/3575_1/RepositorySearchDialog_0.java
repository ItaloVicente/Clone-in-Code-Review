		toggleSelectionButton = new Button(buttonColumn, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(
				toggleSelectionButton);
		toggleSelectionButton
				.setText(UIText.RepositorySearchDialog_ToggleSelectionButton);
		toggleSelectionButton.setEnabled(false);
		toggleSelectionButton.addSelectionListener(new SelectionAdapter() {
