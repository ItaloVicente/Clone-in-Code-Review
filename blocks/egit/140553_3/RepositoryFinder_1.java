			    for (IResource o : children) {
				if (o instanceof IContainer
					&& !o.getName().equals(Constants.DOT_GIT)) {
				    find(progress.newChild(1), (IContainer) o,
					    searchLinkedFolders);
				} else {
				    progress.worked(1);
