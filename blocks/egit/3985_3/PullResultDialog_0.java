		hasUpdates = hasFetchResults() || hasMergeResults()
				|| hasRebaseResults();
	}

	private boolean hasFetchResults() {
		final FetchResult fetchResult = result.getFetchResult();
		return fetchResult != null
				&& !fetchResult.getTrackingRefUpdates().isEmpty();
	}

	private boolean hasMergeResults() {
		final MergeResult mergeResult = result.getMergeResult();
		return mergeResult != null
				&& mergeResult.getMergeStatus() != MergeStatus.ALREADY_UP_TO_DATE;
	}

	private boolean hasRebaseResults() {
		final RebaseResult rebaseResult = result.getRebaseResult();
		return rebaseResult != null
				&& rebaseResult.getStatus() != Status.UP_TO_DATE;
