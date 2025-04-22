	void setExpandedElements(Set<StagingFolderEntry> expanded) {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			treeViewer.setExpandedElements(expanded.toArray());
			return;
		}

		expandedFolders = expanded;
		internalRedraw();
	}

	void collapseAll() {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			UIUtils.collapseAll(treeViewer);
			return;
		}

		expandedFolders = new HashSet<>();
		internalRedraw();
	}

	void expandAll() {
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			UIUtils.expandAll(treeViewer);
			return;
		}

		expandedFolders = new HashSet<>();
		for (Object root : getTreePresentationRoots()) {
			if (root instanceof StagingFolderEntry) {
				walkRecursiveFolders(root,
						(folder) -> expandedFolders.add(folder));
			}
		}
		internalRedraw();
	}

	private void walkRecursiveFolders(Object start,
			Consumer<StagingFolderEntry> consumer) {
		if (start instanceof StagingFolderEntry) {
			StagingFolderEntry folder = (StagingFolderEntry) start;
			consumer.accept(folder);
			for (Object child : folder.getChildren()) {
				walkRecursiveFolders(child, consumer);
			}
		}
	}

