			for (Object obj : projectList) {
				obj = ((IAdaptable)obj).getAdapter(IResource.class);
				if (obj instanceof IProject) {
					final IProject p = (IProject) obj;
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.CORE.getLocation(),
					unmarkTeamPrivate(p);
					RepositoryProvider.unmap(p);
					m.worked(100);
