		RebaseResult.Status status = featureRebaseOperation
				.getOperationResult().getStatus();
		if (RebaseResult.Status.FAILED.equals(status)) {
			return error(UIText.FeatureRebaseHandler_rebaseFailed);
		}
		if (!RebaseResult.Status.STOPPED.equals(status)) {
