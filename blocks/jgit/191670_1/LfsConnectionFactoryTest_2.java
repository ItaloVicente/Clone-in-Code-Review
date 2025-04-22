		assertThrows(LfsConfigInvalidException.class
				() -> LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromWorkingDir()
			throws Exception {
		writeLfsConfig();
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromIndex()
			throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromHEAD()
			throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit LFS Config").call();

		File directory = createTempDirectory("testBareRepo");
		try (Repository bareRepoDb = Git.cloneRepository()
				.setDirectory(directory)
				.setURI(db.getDirectory().toURI().toString()).setBare(true)
				.call().getRepository()) {

			checkLfsUrl(LFS_SERVER_URL1);
		}
	}

	@Test
	public void checkGetLfsConnection_remote_lfsconfigFromWorkingDir()
			throws Exception {
		addRemoteUrl(ORIGIN_URL);
		writeLfsConfig(LFS_SERVER_URL1
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_ConfigFilePrecedence_lfsconfigFromWorkingDir()
			throws Exception {
		writeLfsConfig();
		checkLfsUrl(LFS_SERVER_URL1);

		StoredConfig config = git.getRepository().getConfig();
		config.setString(ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_URL
		config.save();

		checkLfsUrl(LFS_SERVER_URL2);
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_WorkingDir()
			throws Exception {
		writeInvalidLfsConfig();
		LfsConfigInvalidException actualException = assertThrows(
				LfsConfigInvalidException.class
			LfsConnectionFactory.getLfsConnection(db
					Protocol.OPERATION_DOWNLOAD);
		});
		assertTrue(getStackTrace(actualException)
				.contains("Invalid line in config file"));
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_Index()
			throws Exception {
		writeInvalidLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		LfsConfigInvalidException actualException = assertThrows(
				LfsConfigInvalidException.class
			LfsConnectionFactory.getLfsConnection(db
					Protocol.OPERATION_DOWNLOAD);
		});
		assertTrue(getStackTrace(actualException)
				.contains("Invalid line in config file"));
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_HEAD() throws Exception {
		writeInvalidLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit LFS Config").call();

		File directory = createTempDirectory("testBareRepo");
		try (Repository bareRepoDb = Git.cloneRepository()
				.setDirectory(directory)
				.setURI(db.getDirectory().toURI().toString()).setBare(true)
				.call().getRepository()) {
			LfsConfigInvalidException actualException = assertThrows(
					LfsConfigInvalidException.class
					() -> {
						LfsConnectionFactory.getLfsConnection(db
								HttpSupport.METHOD_POST
								Protocol.OPERATION_DOWNLOAD);
					});
			assertTrue(getStackTrace(actualException)
					.contains("Invalid line in config file"));
		}
