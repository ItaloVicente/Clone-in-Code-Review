
	@Override
	public boolean isEnabled() {
		for (IResource res : getSelectedResources()) {
			IProject[] proj = new IProject[] { res.getProject() };
			Repository repository = getRepositoriesFor(proj)[0];
			if (!repository.getRepositoryState().equals(RepositoryState.SAFE)) {
				return false;
			}
		}
		return true;
	}

