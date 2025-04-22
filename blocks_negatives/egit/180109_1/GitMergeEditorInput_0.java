			if (filterPaths.size() > 1) {
				tw.setFilter(AndTreeFilter.create(
						PathFilterGroup.createFromStrings(filterPaths),
						notIgnoredFilter));
			} else if (filterPaths.size() > 0) {
				String path = filterPaths.get(0);
				if (path.isEmpty()) {
					tw.setFilter(notIgnoredFilter);
