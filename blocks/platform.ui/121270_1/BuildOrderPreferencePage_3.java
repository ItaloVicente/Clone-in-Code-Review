
	private void createMaxSimultaneousBuildsGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		groupComposite.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		groupComposite.setLayoutData(gd);

		maxSimultaneousBuilds = new IntegerFieldEditor(IDEInternalPreferences.MAX_SIMULTANEOUS_BUILD,
				IDEWorkbenchMessages.WorkbenchPreference_maxSimultaneousBuilds, groupComposite);

		maxSimultaneousBuilds.setPreferenceStore(getIDEPreferenceStore());
		maxSimultaneousBuilds.setPage(this);
		maxSimultaneousBuilds
				.setTextLimit(Integer.toString(IDEInternalPreferences.MAX_MAX_SIMULTANEOUS_BUILD).length());
		maxSimultaneousBuilds
				.setErrorMessage(NLS.bind(IDEWorkbenchMessages.WorkbenchPreference_maxSimultaneousBuildIntervalError,
						Integer.valueOf(IDEInternalPreferences.MAX_MAX_SIMULTANEOUS_BUILD)));
		maxSimultaneousBuilds.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		maxSimultaneousBuilds.setValidRange(1, IDEInternalPreferences.MAX_MAX_SIMULTANEOUS_BUILD);

		IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		maxSimultaneousBuilds.setStringValue(Integer.toString(description.getMaxConcurrentBuilds()));

		maxSimultaneousBuilds.setPropertyChangeListener(event -> {
			if (event.getProperty().equals(FieldEditor.IS_VALID)) {
				setValid(maxSimultaneousBuilds.isValid());
			}
		});
	}

	protected void createSaveAllBeforeBuildPref(Composite composite) {
		autoSaveAllButton = new Button(composite, SWT.CHECK);
		autoSaveAllButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_savePriorToBuilding);
		autoSaveAllButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_savePriorToBuildingToolTip);
		autoSaveAllButton
				.setSelection(getIDEPreferenceStore().getBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
	}

	protected void createAutoBuildPref(Composite composite) {
		autoBuildButton = new Button(composite, SWT.CHECK);
		autoBuildButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuild);
		autoBuildButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuildToolTip);
		autoBuildButton.setSelection(ResourcesPlugin.getWorkspace().isAutoBuilding());
	}

	protected IPreferenceStore getIDEPreferenceStore() {
		return IDEWorkbenchPlugin.getDefault().getPreferenceStore();
	}

