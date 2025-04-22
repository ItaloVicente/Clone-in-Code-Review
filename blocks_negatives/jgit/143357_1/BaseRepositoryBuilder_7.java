		if (getGitDir() != null) {
			File path = safeFS().resolve(getGitDir(), Constants.CONFIG);
			FileBasedConfig cfg = new FileBasedConfig(path, safeFS());
			try {
				cfg.load();
			} catch (ConfigInvalidException err) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid, path
								.getAbsolutePath(), err.getMessage()));
			}
			return cfg;
		} else {
			return new Config();
		}
	}

	private File guessWorkTreeOrFail() throws IOException {
		final Config cfg = getConfig();

		String path = cfg.getString(CONFIG_CORE_SECTION, null,
				CONFIG_KEY_WORKTREE);
		if (path != null)
			return safeFS().resolve(getGitDir(), path).getCanonicalFile();

		if (cfg.getString(CONFIG_CORE_SECTION, null, CONFIG_KEY_BARE) != null) {
			if (cfg.getBoolean(CONFIG_CORE_SECTION, CONFIG_KEY_BARE, true)) {
				setBare();
				return null;
			}
			return getGitDir().getParentFile();
		}

		if (getGitDir().getName().equals(DOT_GIT)) {
			return getGitDir().getParentFile();
		}

		setBare();
		return null;
	}

	/**
	 * Get the configured FS, or {@link FS#DETECTED}.
	 *
	 * @return the configured FS, or {@link FS#DETECTED}.
	 */
	protected FS safeFS() {
		return getFS() != null ? getFS() : FS.DETECTED;
