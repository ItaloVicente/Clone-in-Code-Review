	protected File createSimpleRemoteRepository(File repositoryDir)
			throws Exception {
		Repository myRepository = lookupRepository(repositoryDir);
		File gitDir = new File(testDirectory, REMOTE_REPO_SIMPLE);
		Repository myRemoteRepository = FileRepositoryBuilder.create(gitDir);
		myRemoteRepository.create(true);
		assertTrue(myRemoteRepository.isBare());

		myRepository.getConfig().setString("remote", "origin", "url",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "origin", "fetch",
				"+refs/heads/*:refs/remotes/origin/*");
		myRepository.getConfig().save();

		PushOperationUI pa = new PushOperationUI(myRepository, "origin", false);
		pa.execute(null);

		return myRemoteRepository.getDirectory();
	}

