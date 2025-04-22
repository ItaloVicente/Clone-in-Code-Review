		Object[] expandedElements = treeViewer.getVisibleExpandedElements();
		for (Object expandedElement : expandedElements) {
			if (expandedElement instanceof StagingFolderEntry) {
				StagingFolderEntry expandedFolder = (StagingFolderEntry) expandedElement;
				if (folder.getPath().isPrefixOf(
						expandedFolder.getPath()))
					addPathAndParentPaths(expandedFolder.getPath(), addToSet);
