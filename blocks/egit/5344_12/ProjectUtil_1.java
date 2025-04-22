	public static IProject[] getProjectsContaining(Repository repository,
			Collection<String> fileList) throws CoreException {
		Set<IProject> result = new LinkedHashSet<IProject>();
		File workTree = repository.getWorkTree();

		for (String member : fileList) {
			File file = new File(workTree, member);

			IContainer container = findContainerFast(file);
			if (container instanceof IProject)
				result.add((IProject) container);
		}

		return result.toArray(new IProject[result.size()]);
	}

	public static IContainer findContainerFast(File file) {
		String absFile = file.getAbsolutePath();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] allProjects = root.getProjects();

		for (IProject prj : allProjects)
			if (checkContainerMatch(prj, absFile))
				return prj;

		if (checkContainerMatch(root, absFile))
			return root;

		return null;
	}

	private static boolean checkContainerMatch(IContainer container,
			String absFile) {
		String absPrj = container.getLocation().toFile().getAbsolutePath();
		if (absPrj.length() == absFile.length()) {
			if (absPrj.equals(absFile))
				return true;
		} else if (absPrj.length() < absFile.length()) {
			char sepChar = absFile.charAt(absPrj.length());
			if (absFile.startsWith(absPrj)
					&& (sepChar == '/' || sepChar == '\\')) {
				return true;
			}
		}
		return false;
	}

