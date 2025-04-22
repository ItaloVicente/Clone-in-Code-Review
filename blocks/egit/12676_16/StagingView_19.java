	private void selectEntryForStaging(StagingEntry entry,
			List<String> addPaths, List<String> rmPaths) {
		switch (entry.getState()) {
		case ADDED:
		case CHANGED:
		case REMOVED:
			break;
		case CONFLICTING:
		case MODIFIED:
		case PARTIALLY_MODIFIED:
		case UNTRACKED:
			addPaths.add(entry.getPath());
			break;
		case MISSING:
		case MISSING_AND_CHANGED:
			rmPaths.add(entry.getPath());
			break;
		}
	}

