	private void updateRefs(GitSynchronizeData gsd) {
		try {
			gsd.updateRevs();
		} catch (IOException e) {
			Activator.logError(
					CoreText.GitSubscriberMergeContext_FailedUpdateRevs, e);

			return;
		}
	}

	private void logRefreshException(Exception e) {
		Activator.logError(
				CoreText.GitSubscriberMergeContext_FailedRefreshSyncView, e);
	}

