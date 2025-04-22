	protected void createEditorHistoryGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		groupComposite.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		groupComposite.setLayoutData(gd);

		recentFilesEditor = new IntegerFieldEditor(IPreferenceConstants.RECENT_FILES,
				WorkbenchMessages.WorkbenchPreference_recentFiles, groupComposite);

		recentFilesEditor.setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
		recentFilesEditor.setPage(this);
		recentFilesEditor.setTextLimit(Integer.toString(EditorHistory.MAX_SIZE).length());
		recentFilesEditor.setErrorMessage(NLS.bind(WorkbenchMessages.WorkbenchPreference_recentFilesError,
				Integer.valueOf(EditorHistory.MAX_SIZE)));
		recentFilesEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		recentFilesEditor.setValidRange(0, EditorHistory.MAX_SIZE);
		recentFilesEditor.load();
		recentFilesEditor.setPropertyChangeListener(validityChangeListener);
	}
}
