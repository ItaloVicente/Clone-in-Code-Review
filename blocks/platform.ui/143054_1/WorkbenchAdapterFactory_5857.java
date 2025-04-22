		if (o instanceof IResource) {
			return resourceFactory;
		}
		if (o instanceof IWorkspace) {
			return workspaceFactory;
		}
		return null;
	}

