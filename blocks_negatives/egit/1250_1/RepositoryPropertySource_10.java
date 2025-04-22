		try {
			effectiveConfig.load();
			userHomeConfig.load();
			repositoryConfig.load();
		} catch (IOException e) {
			showExceptionMessage(e);
		} catch (ConfigInvalidException e) {
			showExceptionMessage(e);
		}
	}

	private void makeActions() {
