				else if (createLinks)
					((IFolder) currentFolder).createLink(createRelativePath(
							path, currentFolder), 0, null);
				else
					((IFolder) currentFolder).create(false, true, null);
			}
		}

		return currentFolder;
	}

	private IContainer createFromRoot(IPath path) throws CoreException {

		int segmentCount = path.segmentCount();

		IContainer currentFolder = ((IWorkspaceRoot) destinationContainer)
				.getProject(path.segment(0));

		for (int i = 1; i < segmentCount; i++) {
			currentFolder = currentFolder.getFolder(new Path(path.segment(i)));
			if (!currentFolder.exists()) {
