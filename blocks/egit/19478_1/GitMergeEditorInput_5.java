	private IDiffContainer getFileParent(IDiffContainer root,
			IPath repositoryPath, IFile file, IPath location) {
		int projectSegment = -1;
		String projectName = null;
		if (file != null) {
			IProject project = file.getProject();
			IPath projectLocation = project.getLocation();
			if (projectLocation != null) {
				IPath projectPath = project.getLocation().makeRelativeTo(
						repositoryPath);
				projectSegment = projectPath.segmentCount() - 1;
				projectName = project.getName();
			}
		}

		IPath path = location.makeRelativeTo(repositoryPath);
		IDiffContainer child = root;
		for (int i = 0; i < path.segmentCount() - 1; i++) {
			if (i == projectSegment)
				child = getOrCreateChild(child, projectName, true);
			else
				child = getOrCreateChild(child, path.segment(i), false);
		}
