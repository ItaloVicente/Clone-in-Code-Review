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
