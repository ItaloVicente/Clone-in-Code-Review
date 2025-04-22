	/**
	 * Determine the encoding used by Eclipse for the resource which belongs to
	 * repoPath in the eclipse workspace or null if no resource is found
	 *
	 * @param db
	 *            the repository
	 * @param repoPath
	 *            the path in the git repository
	 * @return the encoding used in eclipse for the resource or null if
	 *
	 */
	public static String getResourceEncoding(Repository db, String repoPath) {
		if (db.isBare())
			return null;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkTree().getAbsolutePath())
				.append(repoPath);
		IResource resource = root.getFileForLocation(absolutePath);
		if (resource == null)
			return null;

		return getResourceEncoding(resource);
	}

	/**
	 * Determine the encoding used by eclipse for the resource.
	 *
	 * @param resource
	 *            must be an instance of IEncodedStorage
	 * @return the encoding used in Eclipse for the resource if found or null
	 */
	public static String getResourceEncoding(IResource resource) {
		String charset;
		IEncodedStorage encodedStorage = ((IEncodedStorage) resource);
		try {
			charset = encodedStorage.getCharset();
			if (charset == null)
				charset = resource.getParent().getDefaultCharset();
		} catch (CoreException e) {
			charset = Constants.CHARACTER_ENCODING;
		}
		return charset;
	}

