			if (pathFilters != null) {
				List<DiffEntry> filteredEntries = new ArrayList<>();
				for (DiffEntry entry : entries) {
					if (pathFilters.contains(entry.getNewPath())
							|| pathFilters.contains(entry.getOldPath())) {
						filteredEntries.add(entry);
					}
				}
				entries = filteredEntries;
			}

