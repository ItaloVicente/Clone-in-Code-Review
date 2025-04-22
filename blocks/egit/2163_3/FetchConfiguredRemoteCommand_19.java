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
			RemoteConfig config = SimpleConfigureFetchWizard
					.getConfiguredRemote(repository);
			SimpleConfigureFetchWizard wiz = SimpleConfigureFetchWizard
					.getWizard(repository, config);
			if (config == null || wiz != null) {
				new WizardDialog(getShell(event), wiz).open();
			} else {
				FetchConfiguredRemoteAction op = new FetchConfiguredRemoteAction(
						repository, config,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
				op.start();
			}
