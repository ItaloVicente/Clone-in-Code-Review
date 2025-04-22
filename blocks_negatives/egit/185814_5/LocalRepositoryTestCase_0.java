
		try {
			new ConnectProviderOperation(firstProject, gitDir).execute(null);
		} catch (Exception e) {
			Activator.logError("Failed to connect project to repository", e);
		}
		assertConnected(firstProject);
