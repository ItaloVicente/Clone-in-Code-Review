		String currentBranch = repository.getFullBranch();
		if (ObjectId.isId(currentBranch)) {
			currentBranch = null;
		}
		if (currentBranch != null && config == null) {
			try {
				config = new RemoteConfig(repository.getConfig(),
						Constants.DEFAULT_REMOTE_NAME);
			} catch (URISyntaxException e) {
				throw new IOException(e.getLocalizedMessage(), e);
			}
		}
		if (alwaysShowPushWizard || pushTo == PushMode.GERRIT || config == null
				|| currentBranch == null) {
