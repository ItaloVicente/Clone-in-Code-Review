		hideConflictingProjects.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				projectsList.removeFilter(conflictingProjectsFilter);
				if (hideConflictingProjects.getSelection()) {
					projectsList.addFilter(conflictingProjectsFilter);
				}
