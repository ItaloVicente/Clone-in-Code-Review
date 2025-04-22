
	private static IFile getSelectedFile(IStructuredSelection selection) {
		if (selection.size() == 1
				&& selection.getFirstElement() instanceof FileNode) {
			FileNode fileNode = (FileNode) selection.getFirstElement();
			return ResourceUtil.getFileForLocation(fileNode.getPath());
		}
		return null;
	}
