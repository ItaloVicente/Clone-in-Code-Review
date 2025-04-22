	public static void update(Collection<IResource> gitDirectories,
			IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor,
				gitDirectories.size());
		Set<GitProjectData> modified = new HashSet<>();
		try {
			for (IResource resource : gitDirectories) {
				if (progress.isCanceled()) {
					break;
				}
				if (resource.isAccessible()
						&& Constants.DOT_GIT.equals(resource.getName())) {
					createMappingIfNeeded(resource, modified);
				}
				progress.worked(1);
			}
		} finally {
			for (GitProjectData data : modified) {
				try {
					data.store();
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
				}
			}
		}
	}

	private static void createMappingIfNeeded(IResource resource,
			Set<GitProjectData> modified) {
		IPath location = resource.getLocation();
		if (location == null) {
			return;
		}
		File gitCandidate = location.toFile().getParentFile();
		File git = new FileRepositoryBuilder().addCeilingDirectory(gitCandidate)
				.findGitDir(gitCandidate).getGitDir();
		if (git == null) {
			return; // Not a .git directory after all
		}
		GitProjectData data = get(resource.getProject());
		if (data == null) {
			return; // Project not shared with git
		}
		IContainer parent = resource.getParent();
		if (parent.getType() == IResource.PROJECT) {
			return; // Nothing new here; project is already shared.
		}
		RepositoryMapping m;
		try {
			m = (RepositoryMapping) parent.getSessionProperty(MAPPING_KEY);
		} catch (CoreException err) {
			Activator.logError(CoreText.GitProjectData_failedFindingRepoMapping,
					err);
			return;
		}
		if (m != null) {
			return; // Already mapped
		}
		m = RepositoryMapping.create(parent, git);
		try {
			Repository r = RepositoryCache.getInstance().lookupRepository(git);
			if (m != null && r != null && !r.isBare()
					&& gitCandidate.equals(r.getWorkTree())) {
				if (data.map(m)) {
					data.mappings.put(m.getContainerPath(), m);
					modified.add(data);
				}
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

