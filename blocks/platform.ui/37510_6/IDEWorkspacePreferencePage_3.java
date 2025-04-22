	private void createWorkspaceLocationGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(groupComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(groupComposite);

		Label locationLabel = new Label(groupComposite, SWT.NONE);
		locationLabel.setText(IDEWorkbenchMessages.IDEWorkspacePreference_workspaceLocation);
		GridDataFactory.fillDefaults().applyTo(locationLabel);
		Text workspacePath = new Text(groupComposite, SWT.READ_ONLY);
		workspacePath.setBackground(workspacePath.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		workspacePath.setText(Platform.getLocation().toOSString());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(workspacePath);

		Composite showLocationComposite = new Composite(composite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(showLocationComposite);
		GridLayoutFactory.fillDefaults().applyTo(showLocationComposite);
		showLocationInWindowTitle = new BooleanFieldEditor(IDEInternalPreferences.SHOW_LOCATION,
				IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle, showLocationComposite);
		showLocationInWindowTitle.setPage(this);
		showLocationInWindowTitle.setPreferenceStore(getIDEPreferenceStore());
		showLocationInWindowTitle.load();
	}

