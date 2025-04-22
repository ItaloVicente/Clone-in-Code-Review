		fDeselectAllButton = new Button(main, SWT.NONE);
		fDeselectAllButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false,
				false, 1, 1));
		fDeselectAllButton
				.setText(UIText.RepositorySearchDialog_DeselectAll_button);
		fDeselectAllButton.setEnabled(false);
		fDeselectAllButton.addSelectionListener(new SelectionAdapter() {
