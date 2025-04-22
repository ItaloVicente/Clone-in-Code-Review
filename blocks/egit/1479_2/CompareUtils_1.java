	public static String getResourceEncoding(Repository db, String repoPath) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkTree().getAbsolutePath())
				.append(repoPath);
		IResource resource = root.getFileForLocation(absolutePath);
		if (resource == null)
			return null;

		return getResourceEncoding(resource);
	}

	public static String getResourceEncoding(IResource resource) {
		String charset;
		IEncodedStorage encodedStorage = ((IEncodedStorage)resource);
		try {
			charset = encodedStorage.getCharset();
			if (charset == null)
				charset = resource.getParent().getDefaultCharset();
		} catch (CoreException e) {
			charset = Constants.CHARACTER_ENCODING;
		}
		return charset;
	}


