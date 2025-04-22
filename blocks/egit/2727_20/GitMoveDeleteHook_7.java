	private boolean moveProjectHelperMoveRepo(final IResourceTree tree, final IProject source,
			final IProjectDescription description, final int updateFlags,
			final IProgressMonitor monitor, final RepositoryMapping srcm,
			IPath newLocation, IPath sourceLocation) {
		IPath gitDir = srcm.getGitDirAbsolutePath();
		if (unmapProject(tree, source))
			return true; // Error information in tree

		monitor.worked(100);

		IPath relativeGitDir = gitDir.makeRelativeTo(sourceLocation);
		tree.standardMoveProject(source, description, updateFlags,
				monitor);

		IPath newGitDir = newLocation.append(relativeGitDir);
		try {
			mapProject(source, description, monitor, newGitDir);
		} catch (CoreException e) {
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
					0, CoreText.MoveDeleteHook_operationError, e));
		}
		return true; // We're done with the move
