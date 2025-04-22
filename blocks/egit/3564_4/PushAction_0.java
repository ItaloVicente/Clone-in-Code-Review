	private void runPushOperation(final int timeout) {
		GitSynchronizeDataSet gsds = (GitSynchronizeDataSet) getConfiguration()
				.getProperty(SYNCHRONIZATION_DATA);

		for (GitSynchronizeData gsd : gsds) {
			String remoteName = gsd.getSrcRemoteName();
			if (remoteName == null)
				continue;

			RemoteConfig rc;
			Repository repo = gsd.getRepository();
			StoredConfig config = repo.getConfig();
			try {
				rc = new RemoteConfig(config, remoteName);
			} catch (URISyntaxException e) {
				Activator
						.logError(
								"Unable to create RemoteConfiguration for remote: " + remoteName, e); //$NON-NLS-1$
				continue;
			}
