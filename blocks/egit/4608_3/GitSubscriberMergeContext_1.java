			Activator.logError(
					CoreText.GitSubscriberMergeContext_FailedRefreshSyncView, e);
		}
	}

	private void updateRevs(GitSynchronizeData gsd) {
		try {
			gsd.updateRevs();
		} catch (IOException e) {
			Activator.logError(
					CoreText.GitSubscriberMergeContext_FailedUpdateRevs, e);
			return;
