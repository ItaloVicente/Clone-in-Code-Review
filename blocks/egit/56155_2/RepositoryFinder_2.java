		if (c instanceof IProject)
			findInDirectoryAndParents(c, fsLoc);
		else
			findInDirectory(c, fsLoc);
		subMonitor.worked(1);

		if (findInChildren) {
			final IResource[] children = c.members();
			if (children != null && children.length > 0) {
				final int scale = 100 / children.length;
				for (int k = 0; k < children.length; k++) {
					final IResource o = children[k];
					if (o instanceof IContainer
							&& !o.getName().equals(Constants.DOT_GIT)) {
						find(subMonitor.newChild(scale), (IContainer) o,
								searchLinkedFolders);
					} else {
						subMonitor.worked(scale);
