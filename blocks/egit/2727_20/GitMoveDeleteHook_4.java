		Path workTree = new Path(srcm.getRepository().getWorkTree().getAbsolutePath());
		int matchingFirstSegments = workTree.matchingFirstSegments(newLocation);
		if (matchingFirstSegments == workTree.segmentCount()) {
			return moveProjectHelperMoveOnlyProject(tree, source, description, updateFlags,
					monitor, srcm, newLocationFile);
		} else {
			int dstAboveSrcRepo = newLocation.matchingFirstSegments(RepositoryMapping
					.getMapping(source).getGitDirAbsolutePath());
			int srcAboveSrcRepo = sourceLocation.matchingFirstSegments(RepositoryMapping.getMapping(source).getGitDirAbsolutePath());
			if (dstAboveSrcRepo > 0 && srcAboveSrcRepo > 0) {
				return moveProjectHelperMoveRepo(tree, source, description, updateFlags, monitor,
					srcm, newLocation, sourceLocation);
			} else {
				return FINISH_FOR_ME;
			}
		}
	}

	private boolean moveProjectHelperMoveOnlyProject(final IResourceTree tree,
			final IProject source, final IProjectDescription description,
			final int updateFlags, final IProgressMonitor monitor,
			final RepositoryMapping srcm, File newLocationFile) {
		final String sPath = srcm.getRepoRelativePath(source);
		final String absoluteWorkTreePath = srcm.getRepository().getWorkTree().getAbsolutePath();
		final String newLocationAbsolutePath = newLocationFile.getAbsolutePath();
		final String dPath;
		if (newLocationAbsolutePath.equals(absoluteWorkTreePath))
			dPath = ""; //$NON-NLS-1$
		else
			dPath = new Path(
					newLocationAbsolutePath.substring(absoluteWorkTreePath
							.length() + 1) + "/").toPortableString(); //$NON-NLS-1$
		try {
			IPath gitDir = srcm.getGitDirAbsolutePath();
			if (unmapProject(tree, source))
				return true;

			monitor.worked(100);
