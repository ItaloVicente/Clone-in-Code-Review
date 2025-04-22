		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		try {
			root.setSessionProperty(REFRESH_KEY,
					Long.valueOf(System.currentTimeMillis()));
		} catch (CoreException e) {
			handleException(root, e);
		}

