		filterText = new Text(area, SWT.SEARCH | SWT.ICON_CANCEL);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		filterText.setLayoutData(gd);
		filterText.setFocus();
		filterText.addModifyListener(e -> {
			filterRegexPattern = ".*" + filterText.getText() + ".*"; //$NON-NLS-1$ //$NON-NLS-2$
			projectNames.refresh();
		});

		Button selectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.NONE, false, false);
		selectAllButton.setLayoutData(gd);
		selectAllButton.setText(IDEWorkbenchMessages.CleanDialog_selectAllButton);
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				projectNames.setAllChecked(true);
				selection = projectNames.getCheckedElements();
				updateEnablement();
			}
		});

		createProjectSelectionTable(area);

		Button deselectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd);
		deselectAllButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		deselectAllButton.setText(IDEWorkbenchMessages.CleanDialog_deselectedAllButton);
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				projectNames.setAllChecked(false);
				selection = projectNames.getCheckedElements();
				updateEnablement();
			}
		});



