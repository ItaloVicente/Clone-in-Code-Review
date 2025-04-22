	static class StagingViewUpdate {
		Repository repository;
		IndexDiff indexDiff;
		Collection<String> changedResources;

		StagingViewUpdate(Repository theRepository,
				IndexDiff theIndexDiff, Collection<String> theChanges) {
			this.repository = theRepository;
			this.indexDiff = theIndexDiff;
			this.changedResources = theChanges;
		}
	}

	private static int INTERESTING_CHANGES = IResourceDelta.CONTENT
			| IResourceDelta.MOVED_FROM | IResourceDelta.MOVED_TO
			| IResourceDelta.OPEN | IResourceDelta.REPLACED
			| IResourceDelta.TYPE;

