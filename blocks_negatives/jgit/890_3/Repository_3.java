	 * Construct a representation of a Git repository.
	 *
	 * The work tree, object directory, alternate object directories and index
	 * file locations are deduced from the given git directory and the default
	 * rules.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata). May be
	 *            null work workTree is set
	 * @param workTree
	 *            GIT_WORK_TREE (the root of the checkout). May be null for
	 *            default value.
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
	public Repository(final File d, final File workTree) throws IOException {
		this(d, workTree, null, null, null); // go figure it out
	}

	/**
	 * Construct a representation of a Git repository using the given parameters
	 * possibly overriding default conventions.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata). May be null
	 *            for default value in which case it depends on GIT_WORK_TREE.
	 * @param workTree
	 *            GIT_WORK_TREE (the root of the checkout). May be null for
	 *            default value if GIT_DIR is provided.
	 * @param objectDir
	 *            GIT_OBJECT_DIRECTORY (where objects and are stored). May be
	 *            null for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @param alternateObjectDir
	 *            GIT_ALTERNATE_OBJECT_DIRECTORIES (where more objects are read
	 *            from). May be null for default value. Relative names ares
	 *            resolved against GIT_WORK_TREE.
	 * @param indexFile
	 *            GIT_INDEX_FILE (the location of the index file). May be null
	 *            for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
	public Repository(final File d, final File workTree, final File objectDir,
			final File[] alternateObjectDir, final File indexFile) throws IOException {
		this(d, workTree, objectDir, alternateObjectDir, indexFile, FS.DETECTED);
	}

	/**
	 * Construct a representation of a Git repository using the given parameters
	 * possibly overriding default conventions.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata). May be null
	 *            for default value in which case it depends on GIT_WORK_TREE.
	 * @param workTree
	 *            GIT_WORK_TREE (the root of the checkout). May be null for
	 *            default value if GIT_DIR is provided.
	 * @param objectDir
	 *            GIT_OBJECT_DIRECTORY (where objects and are stored). May be
	 *            null for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @param alternateObjectDir
	 *            GIT_ALTERNATE_OBJECT_DIRECTORIES (where more objects are read
	 *            from). May be null for default value. Relative names ares
	 *            resolved against GIT_WORK_TREE.
	 * @param indexFile
	 *            GIT_INDEX_FILE (the location of the index file). May be null
	 *            for default value. Relative names ares resolved against
	 *            GIT_WORK_TREE.
	 * @param fs
	 *            the file system abstraction which will be necessary to
	 *            perform certain file system operations.
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
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
	}

	private void loadUserConfig() throws IOException {
		try {
			userConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat.format(JGitText
					.get().userConfigFileInvalid, userConfig.getFile()
					.getAbsolutePath(), e1));
			e2.initCause(e1);
			throw e2;
		}
	}

	private void loadConfig() throws IOException {
		try {
			config.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(JGitText.get().unknownRepositoryFormat);
			e2.initCause(e1);
			throw e2;
		}
	}


	/**
	 * Create a new Git repository initializing the necessary files and
	 * directories. Repository with working tree is created using this method.
