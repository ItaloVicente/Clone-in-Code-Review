	private DiffNode getFileParent(IDiffContainer root, IFile file) {
		String projectName = file.getProject().getName();
		DiffNode child = getOrCreateChild(root, projectName, true);
		IPath path = file.getProjectRelativePath();
		for (int i = 0; i < path.segmentCount() - 1; i++)
			child = getOrCreateChild(child, path.segment(i), false);
