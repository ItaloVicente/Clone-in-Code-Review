			Repository repository = node.getRepository();
			RemoteConfig config;
			try {
				config = new RemoteConfig(repository.getConfig(), remote
						.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
			PushConfiguredRemoteOperation op = new PushConfiguredRemoteOperation(
					repository, config, Activator.getDefault()
							.getPreferenceStore().getInt(
									UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			op.start();
		} else if (treeNode instanceof RepositoryNode) {
			Repository repository = treeNode.getRepository();
			RemoteConfig config = SimplePushWizard
					.getConfiguredRemote(repository);
			SimplePushWizard wiz = SimplePushWizard.getWizard(repository,
					config);
			if (config == null || wiz != null) {
				new WizardDialog(getShell(event), wiz).open();
			} else {
				PushConfiguredRemoteOperation op = new PushConfiguredRemoteOperation(
						repository, config,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
				op.start();
			}
		}
