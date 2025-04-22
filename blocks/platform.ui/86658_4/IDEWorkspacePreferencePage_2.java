		showLocationNameInTitle = new Button(grpWindowTitle, SWT.CHECK);
		showLocationNameInTitle.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationNameInWindowTitle);
		showLocationNameInTitle.setSelection(isShowName);

		Composite compositeWsName = new Composite(grpWindowTitle, SWT.NONE);
		compositeWsName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
