	private static boolean haveExplicitUserConfig(Repository repository) {
		UserConfig uc = loadRepoScopedConfig(repository).get(UserConfig.KEY);
		return uc.getAuthorName() != null && uc.getCommitterName() != null
				&& uc.getAuthorEmail() != null
				&& uc.getCommitterEmail() != null;
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

