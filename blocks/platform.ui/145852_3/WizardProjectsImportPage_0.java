
			closeProjectsAfterImport = settings.getBoolean(STORE_CLOSE_CREATED_PROJECTS_ID);
			closeProjectsCheckbox.setSelection(closeProjectsAfterImport);

			hideConflictingProjects = settings.getBoolean(STORE_HIDE_CONFLICTING_PROJECTS_ID);
			hideConflictingProjectsButton.setSelection(hideConflictingProjects);
			hideConflictingProjectsButton.notifyListeners(SWT.Selection, new Event());
