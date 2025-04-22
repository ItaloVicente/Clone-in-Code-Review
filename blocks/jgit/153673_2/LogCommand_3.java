		List<TreeFilter> filters = new ArrayList<>();
		if (!pathFilters.isEmpty()) {
			filters.add(AndTreeFilter.create(PathFilterGroup.create(pathFilters)
		}
		if (!excludeTreeFilters.isEmpty()) {
			for (TreeFilter f : excludeTreeFilters) {
				filters.add(AndTreeFilter.create(f
			}
		}
		if (!filters.isEmpty()) {
			if (filters.size() == 1) {
				filters.add(TreeFilter.ANY_DIFF);
			}
			walk.setTreeFilter(AndTreeFilter.create(filters));

		}
