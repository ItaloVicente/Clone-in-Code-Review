
	public static void refreshResources(IResource[] resources,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(CoreText.ProjectUtil_refreshing,
					resources.length);
			for (IResource resource : resources) {
				if (monitor.isCanceled())
					break;
				resource.refreshLocal(IResource.DEPTH_INFINITE,
						new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}

	}

