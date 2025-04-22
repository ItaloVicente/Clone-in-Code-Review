
	public IResource getResourceHandleForLocation(Repository repository,
			String repoRelativePath, boolean isFolder) {
		IResource resource = null;

		final String workDir = repository.getWorkTree().getAbsolutePath();
		final IPath path = new Path(workDir + '/' + repoRelativePath);
		final File file = path.toFile();
		if (file.exists()) {
			if (isFolder) {
				resource = ResourceUtil.getContainerForLocation(path);
			} else {
				resource = ResourceUtil.getFileForLocation(path);
			}
		}

		if (repoRelativePath.endsWith(".project")) { //$NON-NLS-1$
			IPath parentPath = path.removeLastSegments(1);
			IProject p = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(parentPath.lastSegment().toString());
			if (map.get(parentPath) == null) {
				map.put(parentPath, p);
			}
		}

		if (resource == null) {
			final List<IPath> list = new ArrayList<IPath>(map.keySet());
			for (int i = list.size() - 1; i >= 0; i--) {
				IPath projectPath = list.get(i);
				if (projectPath.isPrefixOf(path) && !projectPath.equals(path)) {
					final IPath projectRelativePath = path
							.makeRelativeTo(projectPath);
					if (isFolder) {
						resource = map.get(projectPath).getFolder(
								projectRelativePath);
					} else {
						resource = map.get(projectPath).getFile(
								projectRelativePath);
					}
					break;
				}
			}
		}

		if (resource == null) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
					.getRoot();
			for (IProject project : root.getProjects()) {
				if (RepositoryProvider.getProvider(project, GitProvider.ID) != null) {
					final IPath projectLocation = project.getLocation();
					if (projectLocation != null
							&& projectLocation.isPrefixOf(path)) {
						final IPath projectRelativePath = path
								.makeRelativeTo(projectLocation);
						if (isFolder) {
							resource = project.getFolder(projectRelativePath);
						} else {
							resource = project.getFile(projectRelativePath);
						}
						break;
					}
				}
			}
		}

		return resource;
	}
