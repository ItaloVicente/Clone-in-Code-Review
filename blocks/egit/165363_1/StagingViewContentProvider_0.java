			}
			return getTreePresentationRoots();
		}
	}

	IFile getFile(StagingEntry entry) {
		StagingFolderEntry parent = entry.getParent();
		IContainer container = null;
		if (parent == null) {
			if (rootDetermined) {
				container = rootContainer;
			} else {
				rootDetermined = true;
				Repository repo = entry.getRepository();
				if (repo != null) {
					IPath path = new Path(
							repository.getWorkTree().getAbsolutePath());
					rootContainer = ResourcesPlugin.getWorkspace().getRoot()
							.getContainerForLocation(path);
				}
				container = rootContainer;
			}
		} else {
			container = parent.getContainer();
		}
		if (container == null) {
			return null;
