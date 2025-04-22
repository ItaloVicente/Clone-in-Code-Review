	/**
	 * Create the area with the extra options.
	 *
	 * @param workArea
	 */
	private void createOptionsArea(Composite workArea) {
		Composite optionsGroup = new Composite(workArea, SWT.NONE);
		optionsGroup.setLayout(new GridLayout());
		optionsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (showShare) {
			shareCheckBox = new Button(optionsGroup, SWT.CHECK);
			shareCheckBox.setText(UIText.WizardProjectsImportPage_enableGit);
			shareCheckBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			shareCheckBox.setSelection(share = true);
			shareCheckBox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					share = shareCheckBox.getSelection();
				}
			});
		} else {
			share = false;
		}
	}

