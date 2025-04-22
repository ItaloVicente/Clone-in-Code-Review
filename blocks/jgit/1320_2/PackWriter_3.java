	public void preparePack(ProgressMonitor countingMonitor
			final Collection<? extends ObjectId> interestingObjects
			final Collection<? extends ObjectId> uninterestingObjects
			final Collection<? extends ObjectId> unshallowObjects
			int depth)
			throws IOException {
		if (countingMonitor == null)
			countingMonitor = NullProgressMonitor.INSTANCE;
		ObjectWalk walker = setUpWalker(interestingObjects
				uninterestingObjects
		findObjectsToPack(countingMonitor
	}

