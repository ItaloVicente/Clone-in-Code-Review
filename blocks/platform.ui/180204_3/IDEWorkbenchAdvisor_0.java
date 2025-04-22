		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IContainer root = workspace.getRoot();

		if (workspace instanceof Workspace) {
			Workspace wsp = (Workspace) workspace;
			if (!wsp.isCrashed()) {
				wsp.getRefreshManager().refresh(root);
