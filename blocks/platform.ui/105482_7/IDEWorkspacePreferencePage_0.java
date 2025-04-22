	private static class ComboFieldEditorInGrid extends ComboFieldEditor {

		public ComboFieldEditorInGrid(String name, String labelText, String[][] entryNamesAndValues, Composite parent) {
			super(name, labelText, entryNamesAndValues, parent);
		}

		@Override
		protected void createControl(Composite parent) {
			super.fillIntoGrid(parent, 2);
		}
	}

	private void createMissingNaturePref(Composite parent) {
		missingNatureSeverityCombo = new ComboFieldEditorInGrid(ResourcesPlugin.PREF_MISSING_NATURE_MARKER_SEVERITY,
				IDEWorkbenchMessages.IDEWorkspacePreference_UnknownNatureSeverity,
				new String[][] {
						{ IDEWorkbenchMessages.IDEWorkspacePreference_UnknownNatureSeverity_Ignore,
								Integer.toString(-1) },
						{ MarkerMessages.propertiesDialog_infoLabel, Integer.toString(IMarker.SEVERITY_INFO) },
						{ MarkerMessages.propertiesDialog_warningLabel, Integer.toString(IMarker.SEVERITY_WARNING) },
						{ MarkerMessages.propertiesDialog_errorLabel, Integer.toString(IMarker.SEVERITY_ERROR) },
				}, parent);
		missingNatureSeverityCombo
				.setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, ResourcesPlugin.PI_RESOURCES));
		missingNatureSeverityCombo.setPage(this);
		missingNatureSeverityCombo.load();
	}

