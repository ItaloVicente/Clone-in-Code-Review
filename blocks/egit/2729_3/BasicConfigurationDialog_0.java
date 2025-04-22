	private static boolean isImplicitUserConfig(Repository repository) {
		UserConfig uc = loadRepoScopedConfig(repository).get(UserConfig.KEY);
		return uc.isAuthorNameImplicit() || uc.isAuthorEmailImplicit()
				|| uc.isCommitterNameImplicit()
				|| uc.isCommitterEmailImplicit();
	}

	private static StoredConfig loadUserScopedConfig() {
		StoredConfig c = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			c.load();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return c;
	}

	private static StoredConfig loadRepoScopedConfig(Repository repo) {
		StoredConfig c = repo.getConfig();
		try {
			c.load();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return c;
	}

