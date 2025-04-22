		for (GitSynchronizeData gsd : gsds) {
			if (which.getRepository().equals(gsd.getRepository())) {
				try {
					gsd.updateRevs();
				} catch (IOException e) {
					Activator
							.logError(
									CoreText.GitSubscriberMergeContext_FailedUpdateRevs,
									e);
