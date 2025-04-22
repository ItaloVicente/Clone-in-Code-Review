	public Repository(final File d, final File workTree, final File objectDir,
			final File[] alternateObjectDir, final File indexFile, FS fs)
			throws IOException {

		if (workTree != null) {
			workDir = workTree;
			if (d == null)
				gitDir = new File(workTree, Constants.DOT_GIT);
			else
				gitDir = d;
		} else {
			if (d != null)
				gitDir = d;
			else
				throw new IllegalArgumentException(
						JGitText.get().eitherGIT_DIRorGIT_WORK_TREEmustBePassed);
		}

		this.fs = fs;

		userConfig = SystemReader.getInstance().openUserConfig(fs);
		config = new RepositoryConfig(userConfig, fs.resolve(gitDir, "config"));

		loadUserConfig();
		loadConfig();

		if (workDir == null) {
			String workTreeConfig = getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_WORKTREE);
			if (workTreeConfig != null) {
				workDir = fs.resolve(d, workTreeConfig);
			} else if (getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_BARE) != null) {
				if (!getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION,
						ConfigConstants.CONFIG_KEY_BARE, true))
					workDir = gitDir.getParentFile();
				else
					workDir = null;
			} else if (Constants.DOT_GIT.equals(gitDir.getName())) {
				workDir = gitDir.getParentFile();
			} else {
				workDir = null;
			}
		}

		refs = new RefDirectory(this);
		if (objectDir != null)
			objectDatabase = new ObjectDirectory(fs.resolve(objectDir, ""),
					alternateObjectDir, fs);
		else
			objectDatabase = new ObjectDirectory(fs.resolve(gitDir, "objects"),
					alternateObjectDir, fs);

		if (indexFile != null)
			this.indexFile = indexFile;
		else
			this.indexFile = new File(gitDir, "index");

		if (objectDatabase.exists()) {
			final String repositoryFormatVersion = getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION);
			if (!"0".equals(repositoryFormatVersion)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownRepositoryFormat2,
						repositoryFormatVersion));
			}
		}
