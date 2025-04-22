				uninterestingObjects
		findObjectsToPack(countingMonitor
	}

	public void preparePack(ProgressMonitor countingMonitor
			final Collection<? extends ObjectId> interestingObjects
			int depth)
			throws IOException {
		if (countingMonitor == null)
			countingMonitor = NullProgressMonitor.INSTANCE;
		ObjectWalk walker = setUpWalker(interestingObjects
				null
