			} else if (filterPaths.size() > 0) {
				String path = filterPaths.get(0);
				if (path.length() == 0)
					tw.setFilter(notIgnoredFilter);
				else
					tw.setFilter(AndTreeFilter.create(PathFilter.create(path),
							notIgnoredFilter));
			} else
