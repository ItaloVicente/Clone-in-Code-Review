	@Nullable
	private StagingEntry getStagingEntry(Object element) {
		StagingEntry entry = null;

		if (element instanceof StagingEntry) {
			entry = (StagingEntry) element;
		}

		if (element instanceof TreeItem) {
			TreeItem item = (TreeItem) element;
			if (item.getData() instanceof StagingEntry) {
				entry = (StagingEntry) item.getData();
			}
		}
		return entry;
	}

