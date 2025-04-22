		try {
			ResourcesPlugin.getWorkspace().run(wsr,
					ResourcesPlugin.getWorkspace().getRoot(),
					IWorkspace.AVOID_UPDATE, monitor);
		} catch (CoreException e1) {
			Activator.logError(e1.getMessage(), e1);
