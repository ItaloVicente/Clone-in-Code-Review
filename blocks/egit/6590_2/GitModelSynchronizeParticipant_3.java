	private void restoreSynchronizationData(IMemento[] children) {
		for (IMemento child : children) {
			String containerPath = child.getString(CONTAINER_PATH_KEY);
			Repository repo = getRepositoryForPath(containerPath);
			if (repo == null)
				continue;
			String srcRev = child.getString(SRC_REV_KEY);
			String dstRev = child.getString(DST_REV_KEY);
			boolean includeLocal = getBoolean(
					child.getBoolean(INCLUDE_LOCAL_KEY), true);
			Set<IContainer> includedPaths = getIncludedPaths(child);
			try {
				GitSynchronizeData data = new GitSynchronizeData(repo, srcRev,
						dstRev, includeLocal);
				data.setIncludedPaths(includedPaths);
				gsds.add(data);
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
				continue;
			}
		}
	}

	private Repository getRepositoryForPath(String containerPath) {
		IPath path = Path.fromPortableString(containerPath);
		IContainer mappedContainer = ResourcesPlugin.getWorkspace().getRoot()
				.getContainerForLocation(path);
		GitProjectData projectData = GitProjectData.get((IProject) mappedContainer);
		RepositoryMapping mapping = projectData.getRepositoryMapping(mappedContainer);
		if (mapping != null)
			return mapping.getRepository();
		return null;
	}

	private boolean getBoolean(Boolean value, boolean defaultValue) {
		return value != null ? value.booleanValue() : defaultValue;
	}

	private String getPathForContainer(IContainer container) {
		return container.getLocation().toPortableString();
	}

	private Set<IContainer> getIncludedPaths(IMemento memento) {
		IMemento child = memento.getChild(INCLUDED_PATHS_NODE_KEY);
		if (child != null) {
			Set<IContainer> result = new HashSet<IContainer>();
			IMemento[] pathNode = child.getChildren(INCLUDED_PATH_KEY);
			if (pathNode != null) {
				for (IMemento path : pathNode) {
					String includedPath = path.getString(INCLUDED_PATH_KEY);
					IContainer container = ResourcesPlugin.getWorkspace().getRoot()
							.getContainerForLocation(new Path(includedPath));
					result.add(container);
				}
			}
			return result;
		}
		return null;
	}

