		PushNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();
		RemoteConfig config;
		try {
			config = new RemoteConfig(node.getRepository().getConfig(), remote
					.getObject());
		} catch (URISyntaxException e) {
			throw new ExecutionException(e.getMessage());
		}
		new PushConfiguredRemoteAction(node.getRepository(), config,
				Activator.getDefault().getPreferenceStore().getInt(
						UIPreferences.REMOTE_CONNECTION_TIMEOUT)).start();
