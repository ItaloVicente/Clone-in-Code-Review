
		hideConflictingProjectsCheckBox = new Button(optionsGroup, SWT.CHECK);
		hideConflictingProjectsCheckBox
				.setText(UIText.GitProjectsImportPage_HideConflictingProjects);
		hideConflictingProjectsCheckBox.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		hideConflictingProjectsCheckBox
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						projectsList.removeFilter(conflictingProjectsFilter);
						if (hideConflictingProjectsCheckBox
								.getSelection()) {
							projectsList.addFilter(conflictingProjectsFilter);
						}
					}
				});

