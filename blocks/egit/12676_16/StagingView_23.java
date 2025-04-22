	private void expandPreviousExpandedAndPaths(Object[] previous,
			TreeViewer viewer, Set<IPath> additionalPaths) {
		Set<IPath> paths = new HashSet<IPath>(additionalPaths);
		for (Object element : previous)
			if (element instanceof StagingFolderEntry)
				addPathAndParentPaths(((StagingFolderEntry) element).getPath(), paths);
		List<Object> expand = new ArrayList<Object>();
		StagingViewContentProvider stagedContentProvider = getContentProvider(viewer);
		for (StagingFolderEntry folder : stagedContentProvider
				.getStagingFolderEntries()) {
			if (paths.contains(folder.getPath()))
				expand.add(folder);
		}
		viewer.setExpandedElements(expand.toArray());
	}

