	/**
	 * Updates the filter for the fileviewer with the information
	 * from the revwalk, which files have been renamed. This makes sure
	 * that the fileViewer only shows those files that part of the selected
	 * file and its rename-history.
	 */
	protected void updateFollowFilter() {
		if (currentWalk instanceof FollowingSWTWalk) {
			List<DiffEntry> renamedEntries = ((FollowingSWTWalk) currentWalk).getRenamedEntries();
			List<String> pathList = new ArrayList<String>(renamedEntries.size());
			for (DiffEntry entry : renamedEntries) {
				if (!pathList.contains(entry.getOldPath()))
					pathList.add(entry.getOldPath());

				if (!pathList.contains(entry.getNewPath()))
					pathList.add(entry.getNewPath());
			}

			if (pathList.size() > 0) {
				TreeWalk fileWalker = fileViewer.getTreeWalk();
				fileWalker.setFilter(AndTreeFilter.create(PathFilterGroup
						.createFromStrings(pathList), TreeFilter.ANY_DIFF));
			}
		}
	}

