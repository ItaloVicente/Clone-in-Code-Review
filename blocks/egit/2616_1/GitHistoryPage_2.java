	private boolean shouldRedraw(Repository db, AnyObjectId headId, List<String> paths) {
		boolean pathChange = pathChange(pathFilters, paths);
		boolean headChange = !headId.equals(currentHeadId);
		boolean repoChange = false;

		boolean allBrancheChange = currentShowAllBranches != store
			.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES);
		currentShowAllBranches = store
			.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES);

		if (!db.equals(currentRepo))
		{
			repoChange = true;
			currentRepo = db;
		}

		return pathChange
			|| currentWalk == null || headChange || repoChange || allBrancheChange;
	}

