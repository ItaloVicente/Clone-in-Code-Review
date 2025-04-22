		try {
			new ConnectProviderOperation(toConnect).execute(null);
		} catch (Exception e) {
			Activator.logError("Failed to connect project(s) to repository", e);
		}
		assertConnected(firstProject);

		if (secondProject != null) {
