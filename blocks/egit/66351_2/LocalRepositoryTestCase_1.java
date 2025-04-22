		IProject secondProject = null;
		if (project2Name != null) {
			secondProject = createStandardTestProjectInRepository(myRepository,
					project2Name);


			try {
				new ConnectProviderOperation(secondProject, gitDir)
						.execute(null);
			} catch (Exception e) {
				Activator.logError("Failed to connect project to repository",
						e);
			}
			assertConnected(secondProject);
