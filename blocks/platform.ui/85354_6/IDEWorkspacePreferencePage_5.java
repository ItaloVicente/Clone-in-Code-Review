	private void createSaveIntervalGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		groupComposite.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		groupComposite.setLayoutData(gd);

		saveInterval = new IntegerFieldEditor(IDEInternalPreferences.SAVE_INTERVAL,
				IDEWorkbenchMessages.WorkbenchPreference_saveInterval, groupComposite);

		saveInterval.setPreferenceStore(getIDEPreferenceStore());
		saveInterval.setPage(this);
		saveInterval.setTextLimit(Integer.toString(IDEInternalPreferences.MAX_SAVE_INTERVAL).length());
