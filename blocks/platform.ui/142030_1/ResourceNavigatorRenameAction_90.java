		IWorkspaceRoot root = resource.getProject().getWorkspace().getRoot();
		super.runWithNewPath(path, resource);
		if (this.viewer != null) {
			IResource newResource = root.findMember(path);
			if (newResource != null) {
				this.viewer.setSelection(new StructuredSelection(newResource), true);
