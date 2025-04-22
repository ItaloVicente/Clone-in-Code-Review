		try {
			if (remoteName == null)
				predefinedConfigured = Collections.emptyList();
			else {
				final RemoteConfig rc = new RemoteConfig(localDb.getConfig(),
						remoteName);
				if (pushSpecs)
					predefinedConfigured = rc.getPushRefSpecs();
				else
					predefinedConfigured = rc.getFetchRefSpecs();
				for (final RefSpec spec : predefinedConfigured)
					addRefSpec(spec);
			}
		} catch (URISyntaxException e) {
			predefinedConfigured = null;
			ErrorDialog.openError(panel.getShell(),
					UIText.RefSpecPanel_errorRemoteConfigTitle,
					UIText.RefSpecPanel_errorRemoteConfigDescription,
					new Status(IStatus.ERROR, Activator.getPluginId(), 0, e
							.getMessage(), e));
