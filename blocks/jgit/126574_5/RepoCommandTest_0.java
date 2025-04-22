	@Test
	public void testTwoPathUseTheSameName() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"path1\" ").append("name=\"")
				.append(defaultUri).append("\" />")
				.append("<project path=\"path2\" ").append("name=\"")
				.append(defaultUri).append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecommendShallow(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals("A module should exist for path1"
					c.getString("submodule"
			assertEquals("A module should exist for path2"
					c.getString("submodule"
		}
	}

