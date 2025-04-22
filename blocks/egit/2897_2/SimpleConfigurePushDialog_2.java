	public static Dialog getDialog(Shell shell, Repository repository,
			String remoteName) {
		RemoteConfig configToUse;
		try {
			configToUse = new RemoteConfig(repository.getConfig(), remoteName);
		} catch (URISyntaxException e) {
			Activator.handleError(e.getMessage(), e, true);
			return null;
		}
		return new SimpleConfigurePushDialog(shell, repository, configToUse);
	}

