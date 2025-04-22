	public static String getResourceEncoding(Repository db, String repoPath) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkTree().getAbsolutePath()).append(repoPath);
		IResource resource = root.getFileForLocation(absolutePath);
		if (resource == null)
			return null;
		String encoding = null;
		try {
			encoding = resource.getProject().getDefaultCharset();
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
		}
		if (encoding == null)
			encoding = ResourcesPlugin.getEncoding();
		return encoding;
	}

