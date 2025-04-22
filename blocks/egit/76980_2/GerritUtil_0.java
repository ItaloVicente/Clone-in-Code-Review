
	public static void tryToAutoConfigureForGerrit(
			@NonNull Repository repository) {
		if (repository.isBare()) {
			return;
		}
		StoredConfig config = repository.getConfig();
		boolean isGerrit = false;
		boolean changed = false;
		try {
			for (RemoteConfig remote : RemoteConfig
					.getAllRemoteConfigs(config)) {
				if (isGerritPush(remote)) {
					isGerrit = true;
					if (configureFetchNotes(remote)) {
						changed = true;
						remote.update(config);
					}
				}
			}
		} catch (URISyntaxException ignored) {
		}
		if (isGerrit) {
			if (config.getString(ConfigConstants.CONFIG_GERRIT_SECTION, null,
					ConfigConstants.CONFIG_KEY_CREATECHANGEID) != null) {
			} else {
				setCreateChangeId(config);
				changed = true;
			}
			if (changed) {
				try {
					config.save();
				} catch (IOException e) {
					Activator.logError(
							MessageFormat.format(
									CoreText.GerritUtil_ConfigSaveError,
									repository.getDirectory()),
							e);
				}
			}
		}
	}

	public static void tryToAutoConfigureForGerrit(
			@Nullable File repositoryDir) {
		if (repositoryDir != null) {
			try {
				Repository repository = Activator.getDefault()
						.getRepositoryCache().lookupRepository(repositoryDir);
				if (repository != null) {
					tryToAutoConfigureForGerrit(repository);
				}
			} catch (IOException ignored) {
			}
		}
	}

