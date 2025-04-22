	public void repositoryWithUninitializedModuleAbsoluteUrl()
			throws IOException
		checkSubmoduleInit(absoluteUrl
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathNoRemote()
			throws IOException
		String url = "../a/b";
		checkSubmoduleInit(url
				.getParentFile()
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathBranchSpecificRemote()
			throws CorruptObjectException
		String remoteUrl = "../x/y";
		String myremote = "myremote";
		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.save();
		checkSubmoduleInit(
				"../a/b"
				fixWindowsPath(new File(new File(db.getWorkTree()
						.getParentFile()
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathDefaultRemote()
			throws CorruptObjectException
		String remoteUrl = "../x/y";
		addDefaultRemote(remoteUrl);
		checkSubmoduleInit(
				"../a/b"
				fixWindowsPath(new File(new File(db.getWorkTree()
						.getParentFile()
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathDefaultRemoteWithProtocol()
			throws CorruptObjectException
		String remoteUrl = "file:../x/y";
		addDefaultRemote(remoteUrl);
		checkSubmoduleInit(
				"../a/b"
				"file:"
						+ fixWindowsPath(new File(new File(db.getWorkTree()
								remoteUrl).getParentFile()
								.getAbsolutePath()));
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathDefaultRemoteWithProtocolPort()
			throws CorruptObjectException
		checkSubmoduleInit("../a/b"
	}

	@Test
	public void repositoryWithUninitializedModuleRelativePathDefaultRemoteWithProtocolPort2()
			throws CorruptObjectException
		checkSubmoduleInit("../a/b:1234"
	}

	private void addDefaultRemote(String remoteUrl) throws IOException
			CorruptObjectException {
		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				remoteUrl);
		config.save();
	}

	private void checkSubmoduleInit(String url
			throws CorruptObjectException
