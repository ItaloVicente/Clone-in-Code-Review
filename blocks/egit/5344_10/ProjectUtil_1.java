	public static IProject[] getProjectsContaining(Repository repository,
			Collection<String> fileList) throws CoreException {
		Set<IProject> result = new HashSet<IProject>();
		Set<String> handledPaths = new TreeSet<String>();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (String member : fileList) {
			File file = new File(repository.getWorkTree(), member);

			if (!file.exists() || file.isFile())
				file = file.getParentFile();

			String absPath = file.getAbsolutePath();
			if (handledPaths.contains(absPath))
				continue;

			IContainer[] containers = root.findContainersForLocationURI(file
					.toURI());
			for (IContainer container : containers)
				if (container instanceof IProject)
					result.add((IProject) container);

			handledPaths.add(absPath);
		}

		return result.toArray(new IProject[result.size()]);
	}

