	private void setWalkStartPoints(Repository db, AnyObjectId headId) {
		try {
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(Constants.R_HEADS);
				markStartAllRefs(Constants.R_REMOTES);
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingHeadCommit, headId,
					db.getDirectory().getAbsolutePath()), e);
		}
	}

	private void setupCommentViewer(Repository db, final TreeWalk fileWalker) {
		commentViewer.setTreeWalk(fileWalker);
		commentViewer.setDb(db);
		commentViewer.refresh();
	}

	private TreeWalk setupFileViewer(Repository db, List<String> paths) {
		final TreeWalk fileWalker = createFileWalker(db, paths);
		fileViewer.setTreeWalk(db, fileWalker);
		fileViewer.refresh();
		fileViewer.addSelectionChangedListener(commentViewer);
		return fileWalker;
	}

	private TreeWalk createFileWalker(Repository db, List<String> paths) {
		final TreeWalk fileWalker = new TreeWalk(db);
		fileWalker.setRecursive(true);
		if (paths.size() > 0) {
			pathFilters = paths;
			currentWalk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
					.createFromStrings(paths), TreeFilter.ANY_DIFF));
			fileWalker.setFilter(currentWalk.getTreeFilter().clone());
	
