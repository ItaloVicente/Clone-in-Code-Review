		TreeWalk tw = new TreeWalk(repository);

		if (filterPathStrings.size() > 1) {
			List<TreeFilter> suffixFilters = new ArrayList<TreeFilter>();
			for (String filterPath : filterPathStrings)
				suffixFilters.add(PathFilter.create(filterPath));
			TreeFilter otf = OrTreeFilter.create(suffixFilters);
			tw.setFilter(otf);
		} else if (filterPathStrings.size() > 0) {
			String path = filterPathStrings.get(0);
			if (path.length() != 0)
				tw.setFilter(PathFilter.create(path));
		}
