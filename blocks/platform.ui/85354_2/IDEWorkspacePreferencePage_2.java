		boolean showLocationIsFullPath = getIDEPreferenceStore()
				.getBoolean(IDEInternalPreferences.SHOW_LOCATION_FULLPATH);
		showLocationFullPath = new Button(groupComposite, SWT.CHECK);
		showLocationFullPath.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showFullPathInWindowTitle);
		showLocationFullPath.setSelection(showLocationIsFullPath);
		showLocationFullPath
				.setEnabled(showLocationInWindowTitle.isEnabled() && showLocationInWindowTitle.getSelection());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(showLocationFullPath);

