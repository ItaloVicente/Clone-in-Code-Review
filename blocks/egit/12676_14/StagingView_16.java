	private boolean isUnfiltered(StagingEntry entry) {
		return filterText == null
				|| filterText.getText().length() == 0
				|| entry.getPath().toUpperCase()
						.contains(filterText.getText().toUpperCase());
	}

	private RmCommand selectEntryForStaging(Git git, RmCommand rm,
			List<String> addPaths, StagingEntry entry) {
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
			if (rm == null)
				rm = git.rm().setCached(true);
			rm.addFilepattern(entry.getPath());
			break;
		}
		return rm;
	}

