	private void selectEntryForUnstaging(final RevCommit headRev,
			final DirCacheEditor edit, StagingEntry entry) {
		switch (entry.getState()) {
		case ADDED:
			edit.add(new DirCacheEditor.DeletePath(entry.getPath()));
			break;
		case CHANGED:
		case REMOVED:
			try {
				final TreeWalk tw = TreeWalk.forPath(currentRepository,
						entry.getPath(), headRev.getTree());
				if (tw != null)
					edit.add(new DirCacheEditor.PathEdit(entry
							.getPath()) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.setFileMode(tw.getFileMode(0));
							ent.setObjectId(tw.getObjectId(0));
							ent.setLastModified(0);
						}
					});
			} catch (IOException e) {
				MessageDialog.openError(getSite().getShell(),
						UIText.CommitAction_MergeHeadErrorTitle,
						UIText.CommitAction_ErrorReadingMergeMsg);
			}
			break;
		default:
		}
	}

	private void resetPathsToExpand() {
		pathsToExpandInStaged = new HashSet<IPath>();
		pathsToExpandInUnstaged = new HashSet<IPath>();
	}

	private static void addExpandedPathsBelowFolder(StagingFolderEntry folder,
			TreeViewer treeViewer, Set<IPath> addToSet) {
		Object[] expandedElements = treeViewer.getExpandedElements();
		for (Object expandedElement : expandedElements) {
			if (expandedElement instanceof StagingFolderEntry) {
				StagingFolderEntry expandedFolder = (StagingFolderEntry) expandedElement;
				if (folder.getPath().isPrefixOf(
						expandedFolder.getPath()))
					addPathAndParentPaths(expandedFolder.getPath(), addToSet);
			}
		}
	}

	private static void addPathAndParentPaths(IPath initialPath, Set<IPath> addToSet) {
		for (IPath p = initialPath; p.segmentCount() >= 1; p = p
				.removeLastSegments(1))
			addToSet.add(p);
	}

