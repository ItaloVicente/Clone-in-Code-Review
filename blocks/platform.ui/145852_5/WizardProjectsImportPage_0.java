
			closeProjectsAfterImport = settings.getBoolean(STORE_CLOSE_CREATED_PROJECTS_ID);
			closeProjectsCheckbox.setSelection(closeProjectsAfterImport);

			hideConflictingProjects = settings.getBoolean(STORE_HIDE_CONFLICTING_PROJECTS_ID);
			hideConflictingProjectsCheckbox.setSelection(hideConflictingProjects);
			hideConflictingProjectsCheckbox.notifyListeners(SWT.Selection, new Event());
