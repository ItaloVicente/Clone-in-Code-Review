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
			if (SimpleConfigurePushDialog.shouldConfigure(repository)) {
				Dialog dlg = SimpleConfigurePushDialog.getDialog(
						getShell(event), repository);
				dlg.open();
			} else {
				PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
						repository, SimpleConfigurePushDialog
								.getConfiguredRemote(repository),
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
				op.start();
			}
		}
