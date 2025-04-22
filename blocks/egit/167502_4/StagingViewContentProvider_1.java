	void setExpandedElements(Set<StagingFolderEntry> expanded) {
		if (!haveVirtualTree()) {
			treeViewer.setExpandedElements(expanded.toArray());
			return;
		}

		expandedFolders = expanded;
		internalRedraw();
	}

	void collapseAll() {
		if (!haveVirtualTree()) {
			UIUtils.collapseAll(treeViewer);
			return;
		}

		expandedFolders = new HashSet<>();
		internalRedraw();
	}

	void expandAll() {
		if (!haveVirtualTree()) {
			UIUtils.expandAll(treeViewer);
			return;
		}

		expandedFolders = new HashSet<>();
		for (Object root : getTreePresentationRoots()) {
			visitFoldersRecursive(root, (folder) -> expandedFolders.add(folder));
		}
		internalRedraw();
	}

	private void visitFoldersRecursive(Object start,
			Consumer<StagingFolderEntry> consumer) {
		if (start instanceof StagingFolderEntry) {
			StagingFolderEntry folder = (StagingFolderEntry) start;
			consumer.accept(folder);
			for (Object child : folder.getChildren()) {
				visitFoldersRecursive(child, consumer);
			}
		}
	}

