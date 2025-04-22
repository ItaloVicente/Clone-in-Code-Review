	private boolean includeResourceModelProvider() {
		GitSubscriberMergeContext context = (GitSubscriberMergeContext) getContext();
		for (GitSynchronizeData gsd : context.getSyncData())
			if (!gsd.shouldIncludeLocal())
				return false;

		return true;
	}

