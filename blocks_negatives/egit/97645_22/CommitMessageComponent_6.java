		for (ICommitMessageProvider messageProvider : messageProviders) {
			String message = null;
			try {
				message = messageProvider.getMessage(resourcesArray);
			} catch (RuntimeException e) {
				Activator.logError(e.getMessage(), e);
			}
