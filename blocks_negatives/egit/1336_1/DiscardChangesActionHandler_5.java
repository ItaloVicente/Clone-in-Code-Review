		try {
			for (IResource res : getSelectedResources(null)) {
				IProject[] proj = new IProject[] { res.getProject() };
				Repository[] repositories = getRepositoriesFor(proj);
				if (repositories.length == 0)
					return false;
				Repository repository = repositories[0];
				if (!repository.getRepositoryState().equals(
						RepositoryState.SAFE)) {
					return false;
				}
