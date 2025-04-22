		List<IProject> result = new ArrayList<IProject>();
		final File parentFile = repository.getWorkDir();
		for (IProject p : projects) {
			String projectFilePath = p.getLocation()
					.append(".project").toOSString(); //$NON-NLS-1$
			File projectFile = new File(projectFilePath);
			if (projectFile.exists()) {
				final File file = p.getLocation().toFile();
				if (file.getAbsolutePath().startsWith(
						parentFile.getAbsolutePath())) {
					result.add(p);
				}
			}
		}
		return result.toArray(new IProject[result.size()]);
	}

	public static void refreshValidProjects(IProject[] projects,
			IProgressMonitor monitor) throws CoreException {
