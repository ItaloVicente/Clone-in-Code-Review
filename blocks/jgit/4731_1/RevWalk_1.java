	public int count(final RevCommit start
			throws MissingObjectException
			IOException {
		final RevFilter oldRF = filter;
		final TreeFilter oldTF = treeFilter;
		try {
			finishDelayedFreeFlags();
			reset(~freeFlags & APP_FLAGS);
			filter = RevFilter.ALL;
			treeFilter = TreeFilter.ALL;
			markStart(start);
			if (end != null)
				markUninteresting(end);

			int count = 0;
			for (RevCommit c = next(); c != null; c = next())
				count++;
			return count;
		} finally {
			filter = oldRF;
			treeFilter = oldTF;
		}
	}

