	private void setWalkStartPoints(RevWalk walk, Repository db, AnyObjectId headId) {
		try {
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(walk, Constants.R_HEADS);
				markStartAllRefs(walk, Constants.R_REMOTES);
				markStartAllRefs(walk, Constants.R_TAGS);
			}
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS))
				markStartAdditionalRefs(walk);
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_NOTES))
				markStartAllRefs(walk, Constants.R_NOTES);
			else
				markUninteresting(walk, Constants.R_NOTES);

			walk.markStart(walk.parseCommit(headId));
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorSettingStartPoints, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)), e);
		}
	}

	private void setupCommentViewer(Repository db) {
		commentViewer.setRepository(db);
		commentViewer.refresh();
	}

	private TreeWalk setupFileViewer(RevWalk walk, Repository db, List<FilterPath> paths) {
		final TreeWalk fileWalker = createFileWalker(walk, db, paths);
		fileViewer.setTreeWalk(db, fileWalker);
		fileViewer.setInterestingPaths(fileViewerInterestingPaths);
		fileViewer.refresh();
		return fileWalker;
	}

