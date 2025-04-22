		EclipseGitProgressTransformer gitMonitor;
		if (monitor == null)
			gitMonitor = new EclipseGitProgressTransformer(
					new NullProgressMonitor());
		else
			gitMonitor = new EclipseGitProgressTransformer(monitor);
