			Collection<String> filterPaths = getFilterPaths();
			if (!filterPaths.isEmpty()) {
				if (filterPaths.size() > 1) {
					tw.setFilter(
							PathFilterGroup.createFromStrings(filterPaths));
				} else {
					String path = filterPaths.iterator().next();
					if (!path.isEmpty()) {
						tw.setFilter(PathFilterGroup.createFromStrings(path));
					}
