	public int getProblemSeverity() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFileForLocation(getLocation());
		if (file == null)
			return SEVERITY_NONE;

		try {
			return file.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ONE);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

