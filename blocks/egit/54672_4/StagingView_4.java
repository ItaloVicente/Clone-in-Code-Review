	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				stagedViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		for (StagingEntry entry : entries) {
			if (entry.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result < entry.getProblemSeverity()) {
					result = entry.getProblemSeverity();
				}
			}
		}
		return result;
	}

