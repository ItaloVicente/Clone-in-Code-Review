
	private IFile getFileForBlob(GitModelBlob blob) {
		IPath blobLocation = blob.getLocation();

		IWorkspaceRoot root = getWorkspace().getRoot();
		IFile file = root.getFileForLocation(blobLocation);
		if (file == null)
			file = root.getFile(blobLocation);

		return file;
	}

