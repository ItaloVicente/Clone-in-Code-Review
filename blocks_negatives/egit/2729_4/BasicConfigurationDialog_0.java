		userScopedConfig = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			userScopedConfig.load();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		config = userScopedConfig.get(UserConfig.KEY);
