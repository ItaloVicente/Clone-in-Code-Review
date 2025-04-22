	public static IProject[] getProjectsContaining(Repository repository,
			Collection<String> fileList) throws CoreException {
		List<IProject> result = new ArrayList<IProject>();

		IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject prj : allProjects)
			for (String member : fileList)
				try {
					if (makePrjRelativePath(repository, prj, member) != null) {
						result.add(prj);
						break;
					}
				} catch (IOException e) {
				}

		return result.toArray(new IProject[result.size()]);
	}

	private static String makePrjRelativePath(Repository repository,
			IProject prj, String member) throws IOException {
		String canonicalMember = new File(repository.getWorkTree(), member)
				.getCanonicalPath();
		String canonicalPrj = prj.getLocation().toFile().getCanonicalPath();

		if (canonicalMember.startsWith(canonicalPrj))
			return canonicalMember.substring(canonicalPrj.length());

		return null;
	}

