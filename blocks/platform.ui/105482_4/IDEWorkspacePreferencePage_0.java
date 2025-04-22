	private void createMissingNaturePref(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		missingNatureSeverityCombo = new ComboFieldEditor(ResourcesPlugin.PREF_MISSING_NATURE_MARKER_SEVERITY,
				IDEWorkbenchMessages.IDEWorkspacePreference_UnknownNatureSeverity,
				new String[][] {
						{ IDEWorkbenchMessages.IDEWorkspacePreference_UnknownNatureSeverity_Ignore,
								Integer.toString(-1) },
						{ MarkerMessages.propertiesDialog_infoLabel, Integer.toString(IMarker.SEVERITY_INFO) },
						{ MarkerMessages.propertiesDialog_warningLabel, Integer.toString(IMarker.SEVERITY_WARNING) },
						{ MarkerMessages.propertiesDialog_errorLabel, Integer.toString(IMarker.SEVERITY_ERROR) },
		}, composite);
		missingNatureSeverityCombo
				.setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, ResourcesPlugin.PI_RESOURCES));
		missingNatureSeverityCombo.setPage(this);
		missingNatureSeverityCombo.load();
	}

