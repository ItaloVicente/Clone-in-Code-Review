
		filterText = new Text(area, SWT.SEARCH | SWT.ICON_CANCEL);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		filterText.setLayoutData(gd);
		filterText.setFocus();
		filterText.addModifyListener(e -> {
			projectNames.refresh();
		});

		selectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, false, false);
		selectAllButton.setLayoutData(gd);
		selectAllButton.setText(IDEWorkbenchMessages.CleanDialog_selectAllButton);
		selectAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			projectNames.setAllChecked(true);
			selection = projectNames.getCheckedElements();
			updateEnablement();
		}));

		createProjectSelectionTable(area);

		deselectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.TOP, false, false);
		deselectAllButton.setLayoutData(gd);
		deselectAllButton.setText(IDEWorkbenchMessages.CleanDialog_deselectedAllButton);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			projectNames.setAllChecked(false);
			selection = projectNames.getCheckedElements();
			updateEnablement();
		}));

