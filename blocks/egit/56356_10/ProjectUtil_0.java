	public static void refreshValidProjects(IProject[] projects, boolean delete,
			IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor,
				CoreText.ProjectUtil_refreshingProjects, projects.length);
		for (IProject p : projects) {
			if (progress.isCanceled())
				break;
			IPath projectLocation = p.getLocation();
			if (projectLocation == null) {
				progress.worked(1);
				continue;
