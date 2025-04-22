		return getRepositoriesFor(selectedProjects);
	}

	protected Repository[] getRepositories() {
		IProject[] selectedProjects = getSelectedProjects(getSelection());
		return getRepositoriesFor(selectedProjects);
