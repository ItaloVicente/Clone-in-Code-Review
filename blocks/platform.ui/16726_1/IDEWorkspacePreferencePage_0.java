
	protected void createSystemExplorerGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		groupComposite.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		groupComposite.setLayoutData(gd);

		systemExplorer = new StringFieldEditor(IDEInternalPreferences.WORKBENCH_SYSTEM_EXPLORER,
				IDEWorkbenchMessages.IDEWorkbenchPreference_workbenchSystemExplorer, groupComposite);
		systemExplorer.setPreferenceStore(getIDEPreferenceStore());
		systemExplorer.setPage(this);

		systemExplorer.load();

		systemExplorer.setPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(FieldEditor.IS_VALID)) {
					setValid(systemExplorer.isValid());
				}
			}
		});
	}

