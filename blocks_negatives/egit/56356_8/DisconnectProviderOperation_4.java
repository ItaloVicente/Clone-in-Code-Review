		try {
			for (IProject p : projectList) {
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
				unmarkTeamPrivate(p);
				RepositoryProvider.unmap(p);
				monitor.worked(100);

				p.refreshLocal(IResource.DEPTH_INFINITE,
						new SubProgressMonitor(monitor, 100));
			}
		} finally {
			monitor.done();
