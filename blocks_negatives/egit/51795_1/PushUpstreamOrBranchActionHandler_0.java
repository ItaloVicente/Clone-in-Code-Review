		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		Shell shell = getShell(event);
		RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemote(repository);
