	private String getMemberPath(DiffEntry entry) {
		if (!entry.getNewPath().equals(DiffEntry.DEV_NULL))
			return entry.getNewPath();
		else
			return entry.getOldPath();
	}

