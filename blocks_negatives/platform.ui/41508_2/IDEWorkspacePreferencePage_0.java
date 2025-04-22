		Composite showLocationComposite = new Composite(composite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(showLocationComposite);
		GridLayoutFactory.fillDefaults().applyTo(showLocationComposite);
		showLocationInWindowTitle = new BooleanFieldEditor(IDEInternalPreferences.SHOW_LOCATION,
				IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle, showLocationComposite);
		showLocationInWindowTitle.setPage(this);
		showLocationInWindowTitle.setPreferenceStore(getIDEPreferenceStore());
		showLocationInWindowTitle.load();
