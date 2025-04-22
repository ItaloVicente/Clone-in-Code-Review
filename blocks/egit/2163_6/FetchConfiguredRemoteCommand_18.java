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
			if (SimpleConfigureFetchDialog.shouldConfigure(repository)) {
				Dialog dlg = SimpleConfigureFetchDialog.getDialog(
						getShell(event), repository);
