	public RemoteConfig setItems(List<RemoteConfig> remoteConfigs) {
		return setItems(remoteConfigs, null);
	}

	private void showNewRemoteDialog() {
		AddRemoteWizard wizard = new AddRemoteWizard(enableAddNewRemote);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		int result = dialog.open();
		if (result == Window.OK) {
			try {
				RemoteConfig newConfig = configureNewRemote(
						wizard.getRemoteName(), wizard.getUri());
				addAndSelectNewConfig(newConfig);
			} catch (IOException | URISyntaxException ex) {
				MessageDialog.openError(wizard.getShell(),
						UIText.RemoteSelectionCombo_couldNotCreateNewRemote_title,
						UIText.RemoteSelectionCombo_couldNotCreateNewRemote_message);
				Activator.logError(
						UIText.RemoteSelectionCombo_couldNotCreateNewRemote_title,
						ex);
			}
		} else {
			setSelectedRemote(lastSelection);
		}
	}

	private void addAndSelectNewConfig(RemoteConfig newConfig) {
		remoteConfigs = new ArrayList<>(remoteConfigs);
		remoteConfigs.add(newConfig);
		newlyCreatedConfig = newConfig;
		setItems(remoteConfigs);
	}

	private RemoteConfig configureNewRemote(String remoteName, URIish uri)
			throws URISyntaxException, IOException {
		StoredConfig config = enableAddNewRemote.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, remoteName);
		remoteConfig.addURI(uri);
		RefSpec defaultFetchSpec = new RefSpec().setForceUpdate(true)
				.setSourceDestination(Constants.R_HEADS + "*", //$NON-NLS-1$
						Constants.R_REMOTES + remoteName + "/*"); //$NON-NLS-1$
		remoteConfig.addFetchRefSpec(defaultFetchSpec);
		remoteConfig.update(config);
		config.save();
		return remoteConfig;
	}

