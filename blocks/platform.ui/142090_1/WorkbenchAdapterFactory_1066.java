		if (o instanceof IResource) {
			return new ResourceFactory((IResource) o);
		}
		if (o instanceof IWorkspace) {
			return workspaceFactory;
		}
		return null;
	}

