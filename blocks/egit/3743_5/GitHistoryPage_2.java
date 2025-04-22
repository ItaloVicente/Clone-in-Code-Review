		if (store.getBoolean(UIPreferences.RESOURCEHISTORY_FOLLOW_RENAMES)
				&& !paths.isEmpty()
				&& allRegularFiles(paths)) {
			pathFilters = paths;

			List<String> selectedPaths = new ArrayList<String>(paths.size());
			for (FilterPath filterPath : paths)
				selectedPaths.add(filterPath.getPath());

			TreeFilter followFilter = createFollowFilterFor(selectedPaths);
			currentWalk.setTreeFilter(followFilter);
			fileWalker.setFilter(AndTreeFilter.create(PathFilterGroup
					.createFromStrings(selectedPaths), TreeFilter.ANY_DIFF));
		} else if (paths.size() > 0) {
