	private static IFile getFileForLocationURI(IWorkspaceRoot root, URI uri) {
		IFile[] files = root.findFilesForLocationURI(uri);
		for (IFile file : files)
			if (file.exists())
				return file;
		return null;
	}

