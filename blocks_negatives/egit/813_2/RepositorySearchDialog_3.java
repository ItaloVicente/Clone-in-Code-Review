		fSelectAllButton = new Button(main, SWT.NONE);
		fSelectAllButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false,
				false, 1, 1));
		fSelectAllButton
				.setText(UIText.RepositorySearchDialog_SelectAll_button);
		fSelectAllButton.setEnabled(false);
		fSelectAllButton.addSelectionListener(new SelectionAdapter() {
