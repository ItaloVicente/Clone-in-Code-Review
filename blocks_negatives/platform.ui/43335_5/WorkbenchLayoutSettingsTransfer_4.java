	/**
	 * Create the parent directories for the workbench layout file and then
	 * return the File.
	 *
	 * @param newWorkspaceRoot
	 * @return File the new layout file. Return <code>null</code> if the file
	 *         cannot be created.
	 */
	private File createFileAndDirectories(IPath newWorkspaceRoot) {
		IPath newWorkspaceLocation = getNewWorkbenchStateLocation(newWorkspaceRoot);
		File workspaceFile = new File(newWorkspaceLocation.toOSString());
		if (!workspaceFile.exists()) {
			if (!workspaceFile.mkdirs())
				return null;
		}

		return workspaceFile;
	}

	@Override
	public String getName() {
		return WorkbenchMessages.WorkbenchLayoutSettings_Name;
