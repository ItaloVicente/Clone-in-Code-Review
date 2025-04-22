		boolean complete = !selectedRepositories.isEmpty();
		if (complete)
			for (Repository repository : selectedRepositories)
				if (!selectedBranches.containsKey(repository)) {
					complete = false;
					break;
				}
		setPageComplete(complete);
	}

	void selectProjects(IProject[] projs) {
		this.selectProjects = projs;
