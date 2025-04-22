			if (sourcePathFilter != null) {
				List<DiffEntry> newAdded = new ArrayList<>();
				String sourcePath = sourcePathFilter.getPath();
				for (DiffEntry add : added) {
					if (add.getNewPath().equals(sourcePath)) {
						newAdded.add(add);
					}
				}
				if (!newAdded.isEmpty()) {
					added = newAdded;
				}
			}

