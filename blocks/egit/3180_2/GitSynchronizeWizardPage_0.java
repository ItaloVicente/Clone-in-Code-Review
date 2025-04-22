	private void validatePage() {
		boolean complete = !selectedRepositories.isEmpty();
		if (complete)
			for (Repository repository : selectedRepositories)
				if (!selectedBranches.containsKey(repository)) {
					complete = false;
					break;
				}
		setPageComplete(complete);
	}

