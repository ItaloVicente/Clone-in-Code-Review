	public boolean isImportProjects() {
		return importProjectsButton != null
				&& importProjectsButton.getSelection();
	}

	public IWorkingSet[] getWorkingSets() {
		if (workingSetGroup == null)
			return new IWorkingSet[0];
		return workingSetGroup.getSelectedWorkingSets();
	}

