	public static void refreshValidProjects(IProject[] projects, boolean delete,
			IProgressMonitor monitor) throws CoreException {
		if (projects == null || projects.length == 0) {
			return;
		}
		SubMonitor progress = SubMonitor.convert(monitor,
				CoreText.ProjectUtil_refreshingProjects, projects.length);
		for (IProject p : projects) {
			if (progress.isCanceled())
				break;
			IPath projectLocation = p.getLocation();
			if (projectLocation == null)
				continue;
			String projectFilePath = projectLocation
					.append(IProjectDescription.DESCRIPTION_FILE_NAME)
					.toOSString();
			File projectFile = new File(projectFilePath);
			if (projectFile.exists())
				p.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(1));
			else if (delete)
				p.delete(false, true, progress.newChild(1));
			else
				closeMissingProject(p, projectFile, progress.newChild(1));
			progress.worked(1);
