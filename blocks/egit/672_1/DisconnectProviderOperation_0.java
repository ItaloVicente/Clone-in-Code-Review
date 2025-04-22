			for (IProject p : projectList) {
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
							"disconnect " + p.getName()); //$NON-NLS-1$
				unmarkTeamPrivate(p);
				RepositoryProvider.unmap(p);
				m.worked(100);
