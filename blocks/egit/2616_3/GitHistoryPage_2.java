	private boolean shouldRedraw(Repository db, AnyObjectId headId, List<String> paths) {
		boolean pathChanged = pathChanged(pathFilters, paths);
		boolean headChanged = !headId.equals(currentHeadId);
		boolean repoChanged = false;

		boolean allBranchesChanged = currentShowAllBranches != store
			.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES);
		currentShowAllBranches = store
			.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES);

		if (!db.equals(currentRepo)) {
			repoChanged = true;
			currentRepo = db;
		}

		return pathChanged
			|| currentWalk == null || headChanged || repoChanged || allBranchesChanged;
	}

