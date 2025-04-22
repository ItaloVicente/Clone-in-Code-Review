
	boolean tryAddResource(IFile resource, GitProjectData projectData, ArrayList<IFile> category) {
		if (files.contains(resource))
			return false;

		try {
			RepositoryMapping repositoryMapping = projectData
					.getRepositoryMapping(resource);

			if (isChanged(repositoryMapping, resource)) {
				files.add(resource);
				category.add(resource);
				return true;
			}
		} catch (Exception e) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
		}
		return false;
	}

	private boolean isChanged(RepositoryMapping map, IFile resource) {
		try {
			Repository repository = map.getRepository();
			GitIndex index = repository.getIndex();
			String repoRelativePath = map.getRepoRelativePath(resource);
			Entry entry = index.getEntry(repoRelativePath);
			if (entry != null)
				return entry.isModified(map.getWorkDir());
			return false;
		} catch (UnsupportedEncodingException e) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
		} catch (IOException e) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		return getProjectsInRepositoryOfSelectedResources().length > 0;
	}

