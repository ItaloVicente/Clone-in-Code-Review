		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecordRemoteBranch(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals(
					"Recording remote branches should work for short branch descriptions"
					"master"
					c.getString("submodule"
			assertEquals(
					"Recording remote branches should work for full ref specs"
					"refs/heads/master"
					c.getString("submodule"
