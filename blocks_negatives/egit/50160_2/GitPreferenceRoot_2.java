	private static StoredConfig loadUserScopedConfig() {
		StoredConfig c = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			c.load();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
