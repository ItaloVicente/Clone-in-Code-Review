	/**
	 * Return the workbench settings location for the new root
	 * @param newWorkspaceRoot
	 * @return IPath or <code>null</code> if it can't be determined.
	 */
	protected IPath getNewWorkbenchStateLocation(IPath newWorkspaceRoot) {
		IPath currentWorkspaceRoot = Platform.getLocation();
