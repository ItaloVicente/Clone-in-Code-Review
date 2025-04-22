		for (IProject p : projectList) {
			if (GitTraceLocation.CORE.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.CORE.getLocation(),
						"disconnect " + p.getName()); //$NON-NLS-1$
			unmarkTeamPrivate(p);
			RepositoryProvider.unmap(p);
			progress.worked(100);
			p.refreshLocal(IResource.DEPTH_INFINITE, progress.newChild(100));
