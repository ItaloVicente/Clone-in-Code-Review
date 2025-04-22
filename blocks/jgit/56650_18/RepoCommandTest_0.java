	@Test
	public void testRecordRemoteBranch() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"with-branch\" ")
					.append("revision=\"master\" ")
					.append("name=\"").append(notDefaultUri).append("\" />")
				.append("<project path=\"with-long-branch\" ")
					.append("revision=\"refs/heads/master\" ")
					.append("name=\"").append(defaultUri).append("\" />")
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecordRemoteBranch(true)
				.call();
			File directory = createTempDirectory("testBareRepo");
			Repository localDb = Git.cloneRepository().setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals("standard branches work"
				c.getString("submodule"
			assertEquals("long branches work"
				c.getString("submodule"
		}
	}

