			Repository repository = node.getRepository();
			RemoteConfig config;
			try {
				config = new RemoteConfig(repository.getConfig(), remote
						.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
			PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
					repository, config, Activator.getDefault()
							.getPreferenceStore().getInt(
									UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			op.start();
		} else if (treeNode instanceof RepositoryNode) {
			Repository repository = treeNode.getRepository();
			RemoteConfig config = SimpleConfigurePushDialog
					.getConfiguredRemote(repository);
			if (config == null) {
				MessageDialog
						.openInformation(
								getShell(event),
								UIText.SimplePushActionHandler_NothingToPushDialogTitle,
								UIText.SimplePushActionHandler_NothingToPushDialogMessage);
				return null;
			}
			PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
					repository, config, Activator.getDefault()
							.getPreferenceStore().getInt(
									UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			op.start();
		}
