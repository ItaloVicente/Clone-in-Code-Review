
		hideConflictingProjects = new Button(optionsGroup, SWT.CHECK);
		hideConflictingProjects
				.setText(DataTransferMessages.WizardProjectsImportPage_hideConflictingProjects);
		hideConflictingProjects.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		hideConflictingProjects.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				projectsList.removeFilter(conflictingProjectsFilter);
				if (hideConflictingProjects.getSelection()) {
					projectsList.addFilter(conflictingProjectsFilter);
				}
			}
		});
		Dialog.applyDialogFont(hideConflictingProjects);
