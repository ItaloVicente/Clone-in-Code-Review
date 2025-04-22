			try {
				new ConnectProviderOperation(secondProject, gitDir)
						.execute(null);
			} catch (Exception e) {
				Activator.logError("Failed to connect project to repository",
						e);
			}
