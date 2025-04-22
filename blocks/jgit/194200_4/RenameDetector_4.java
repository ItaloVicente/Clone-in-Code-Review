			if (pathFilters != null) {
				List<DiffEntry> filteredAdded = new ArrayList<>();
				List<DiffEntry> filteredDeleted = new ArrayList<>();
				for (DiffEntry add : added) {
					if (pathFilters.contains(add.getNewPath())) {
						filteredAdded.add(add);
					}
				}
				for (DiffEntry delete : deleted) {
					if (pathFilters.contains(delete.getOldPath())) {
						filteredDeleted.add(delete);
					}
				}
				List<DiffEntry> originalAdded = new ArrayList<>(added);
				List<DiffEntry> originalDeleted = new ArrayList<>(deleted);
				findRenames(reader
				added = filteredAdded;
