		addField(new BooleanFieldEditor(UIPreferences.CHECKOUT_PROJECT_RESTORE,
				UIText.ProjectsPreferencePage_RestoreBranchProjects,
				getFieldEditorParent()) {
			public IPreferenceStore getPreferenceStore() {
				return org.eclipse.egit.ui.Activator.getDefault()
						.getPreferenceStore();
			}
		});
