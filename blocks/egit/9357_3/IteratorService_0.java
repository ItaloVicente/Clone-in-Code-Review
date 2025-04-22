		IWorkspaceRoot root;
		try {
			root = ResourcesPlugin.getWorkspace().getRoot();
		} catch (IllegalStateException e) {
			return null;
		}
