		}
		try {
			discardChanges();
		} catch (GitAPIException e) {
			errorOccurred = true;
			Activator.logError(CoreText.DiscardChangesOperation_discardFailed, e);
