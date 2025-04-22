		SubMonitor progress = SubMonitor.convert(m, 101);
		progress.subTask(CoreText.RepositoryFinder_finding);
		if (loc == null) {
			throw new CoreException(Activator.error(
					NLS.bind(CoreText.RepositoryFinder_ResourceDoesNotExist, c),
					null));
		}
		final File fsLoc = loc.toFile();
		assert fsLoc.isAbsolute();

		if (c instanceof IProject)
			findInDirectoryAndParents(c, fsLoc);
		else
			findInDirectory(c, fsLoc);
		progress.worked(1);

		if (findInChildren) {
			final IResource[] children = c.members();
			if (children != null && children.length > 0) {
				progress.setWorkRemaining(children.length);
				for (int k = 0; k < children.length; k++) {
					final IResource o = children[k];
					if (o instanceof IContainer
							&& !o.getName().equals(Constants.DOT_GIT)) {
						find(progress.newChild(1), (IContainer) o,
								searchLinkedFolders);
					} else {
						progress.worked(1);
