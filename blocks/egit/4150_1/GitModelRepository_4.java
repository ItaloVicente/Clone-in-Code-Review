	@Override
	public void dispose() {
		for (Commit commit : commitCache)
			commit.dispose();

		commitCache.clear();
		stagedChanges.clear();
		workingChanges.clear();
	}

