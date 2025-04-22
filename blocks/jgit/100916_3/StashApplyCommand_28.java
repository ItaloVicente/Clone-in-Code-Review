				actuallyModifiedPaths.add(entry.getPathString());
			}
		} finally {
			if (!actuallyModifiedPaths.isEmpty()) {
				repo.fireEvent(new WorkingTreeModifiedEvent(
						actuallyModifiedPaths
