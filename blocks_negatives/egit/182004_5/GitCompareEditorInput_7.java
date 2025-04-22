			if (filterPathStrings.size() > 1) {
				tw.setFilter(
						PathFilterGroup.createFromStrings(filterPathStrings));
			} else if (filterPathStrings.size() > 0) {
				String path = filterPathStrings.get(0);
				if (!path.isEmpty()) {
					tw.setFilter(PathFilterGroup.createFromStrings(path));
