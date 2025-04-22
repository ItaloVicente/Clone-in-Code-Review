	private static boolean tryToMap(IResource resource,
			Set<GitProjectData> modified) {
		IPath location = resource.getLocation();
		if (location == null) {
			return false;
		}
		if (!Constants.DOT_GIT.equals(resource.getName())) {
			return resource.getType() == IResource.FOLDER;
		}
		File gitCandidate = location.toFile().getParentFile();
		File git = new FileRepositoryBuilder().addCeilingDirectory(gitCandidate)
				.findGitDir(gitCandidate).getGitDir();
		if (git == null) {
			return false;
		}
		GitProjectData data = get(resource.getProject());
		if (data == null) {
			return false;
		}
		RepositoryMapping m = RepositoryMapping.create(resource.getParent(),
				git);
		try {
			Repository r = Activator.getDefault().getRepositoryCache()
					.lookupRepository(git);
			if (m != null && r != null
					&& gitCandidate.equals(r.getWorkTree())) {
				data.mappings.put(m.getContainerPath(), m);
				data.map(m);
				modified.add(data);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
		return false;
	}

