		final IndexDiff indexDiff;
		try {
			WorkingTreeIterator iterator = new FileTreeIterator(repository);
			indexDiff = new IndexDiff(repository, Constants.HEAD, iterator);
			indexDiff.diff(jgitMonitor, 0, 0, jobTitle);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
