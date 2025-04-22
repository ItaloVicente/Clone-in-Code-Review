
		treeViewer.addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				expandedFolders.remove(event.getElement());
			}

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				Object element = event.getElement();
				if (element instanceof StagingFolderEntry) {
					expandedFolders.add((StagingFolderEntry) element);
				}
			}
		});
	}

	private boolean needsExpansion(StagingFolderEntry folder) {
		int autoExpandLevel = treeViewer.getAutoExpandLevel();
		if (autoExpandLevel == AbstractTreeViewer.ALL_LEVELS) {
			return true;
		}
		if (expandedFolders.contains(folder)) {
			return true;
		}
		StagingFolderEntry parent = folder.getParent();
		while (autoExpandLevel > 1) {
			if (parent == null) {
				return true;
			}
			parent = parent.getParent();
			autoExpandLevel--;
		}
		return false;
