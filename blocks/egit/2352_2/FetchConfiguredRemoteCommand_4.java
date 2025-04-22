		try {
			RemoteConfig config = new RemoteConfig(node.getRepository()
					.getConfig(), remote.getObject());
			int timeout = Activator.getDefault().getPreferenceStore().getInt(
					UIPreferences.REMOTE_CONNECTION_TIMEOUT);
			new FetchOperationUI(node.getRepository(), config, timeout, false)
					.start();
		} catch (URISyntaxException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
