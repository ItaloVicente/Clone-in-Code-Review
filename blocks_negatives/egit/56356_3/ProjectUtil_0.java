	public static void refreshValidProjects(IProject[] projects,
			boolean delete, IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(CoreText.ProjectUtil_refreshingProjects,
					projects.length);
			for (IProject p : projects) {
				if (monitor.isCanceled())
					break;
				IPath projectLocation = p.getLocation();
				if (projectLocation == null)
					continue;
				String projectFilePath = projectLocation.append(
						IProjectDescription.DESCRIPTION_FILE_NAME).toOSString();
				File projectFile = new File(projectFilePath);
				if (projectFile.exists())
					p.refreshLocal(IResource.DEPTH_INFINITE,
							new SubProgressMonitor(monitor, 1));
				else if (delete)
					p.delete(false, true, new SubProgressMonitor(monitor, 1));
				else
					closeMissingProject(p, projectFile, monitor);
				monitor.worked(1);
			}
		} finally {
			monitor.done();
