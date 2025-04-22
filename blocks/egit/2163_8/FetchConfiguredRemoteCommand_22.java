			try {
				RemoteConfig config = new RemoteConfig(node.getRepository()
						.getConfig(), remote.getObject());
				new FetchConfiguredRemoteAction(node.getRepository(), config,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT))
						.start();
			} catch (URISyntaxException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		} else if (treeNode instanceof RepositoryNode) {
			Repository repository = treeNode.getRepository();
			RemoteConfig config = SimpleConfigureFetchDialog
					.getConfiguredRemote(repository);
			if (config == null) {
				MessageDialog
						.openInformation(
								getShell(event),
								UIText.SimpleFetchActionHandler_NothingToFetchDialogTitle,
								UIText.SimpleFetchActionHandler_NothingToFetchDialogMessage);
				return null;
			}
			FetchConfiguredRemoteAction op = new FetchConfiguredRemoteAction(
					repository, config, Activator.getDefault()
							.getPreferenceStore().getInt(
									UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			op.start();
