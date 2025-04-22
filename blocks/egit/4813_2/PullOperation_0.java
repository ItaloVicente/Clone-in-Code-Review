	private boolean refreshNeeded(PullResult pullResult) {
		if (pullResult == null)
			return true;
		MergeResult mergeResult = pullResult.getMergeResult();
		if (mergeResult == null)
			return true;
		if (mergeResult.getMergeStatus() == MergeStatus.ALREADY_UP_TO_DATE)
			return false;
		return true;
	}

