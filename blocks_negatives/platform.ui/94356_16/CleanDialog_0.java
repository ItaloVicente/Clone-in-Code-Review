		selectAllButton = new Button(area, SWT.PUSH);
		gd = new GridData(SWT.FILL, SWT.CENTER, false, false);
		selectAllButton.setLayoutData(gd);
		selectAllButton.setText(IDEWorkbenchMessages.CleanDialog_selectAllButton);
		selectAllButton.addSelectionListener(widgetSelectedAdapter(e -> {
			projectNames.setAllChecked(true);
			selection = projectNames.getCheckedElements();
			updateEnablement();
		}));

