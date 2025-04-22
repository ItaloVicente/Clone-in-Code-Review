
	private void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null && settings.get(STORE_NESTED_PROJECTS) != null) {
			nestedProjects = settings.getBoolean(STORE_NESTED_PROJECTS);
			nestedProjectsCheckbox.setSelection(nestedProjects);
			lastNestedProjects = nestedProjects;
		}
	}

	void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null)
			settings.put(STORE_NESTED_PROJECTS,
					nestedProjectsCheckbox.getSelection());
	}
