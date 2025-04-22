		SubMonitor subMonitor = SubMonitor.convert(monitor,
				CoreText.ProjectUtil_refreshingProjects, projects.length);
		for (IProject p : projects) {
			if (subMonitor.isCanceled())
				break;
			IPath projectLocation = p.getLocation();
			if (projectLocation == null)
				continue;
			String projectFilePath = projectLocation
					.append(IProjectDescription.DESCRIPTION_FILE_NAME)
					.toOSString();
			File projectFile = new File(projectFilePath);
			if (projectFile.exists())
				p.refreshLocal(IResource.DEPTH_INFINITE,
						subMonitor.newChild(1));
			else if (delete)
				p.delete(false, true, subMonitor.newChild(1));
			else
				closeMissingProject(p, projectFile, subMonitor.newChild(1));
