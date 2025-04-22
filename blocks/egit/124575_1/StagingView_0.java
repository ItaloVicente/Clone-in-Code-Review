		if (treeViewer.getExpandedState(folder)) {
			addPathAndParentPaths(folder.getPath(), addToSet);
		}
		addExpandedSubfolders(folder, treeViewer, addToSet);
	}

	private static void addExpandedSubfolders(StagingFolderEntry folder,
			TreeViewer treeViewer, Set<IPath> addToSet) {
		for (Object child : folder.getChildren()) {
			if (child instanceof StagingFolderEntry
					&& treeViewer.getExpandedState(child)) {
				addToSet.add(((StagingFolderEntry) child).getPath());
				addExpandedSubfolders((StagingFolderEntry) child, treeViewer,
						addToSet);
