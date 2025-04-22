	@Test
	public void testBareRepo() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File directory = createTempDirectory("testBareRepo");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setURI(remoteDb.getDirectory().toURI().toString());
		Repository localDb = clone.call().getRepository();
		File gitmodules = new File(localDb.getWorkTree()
		assertTrue("The .gitmodules file exists"
		BufferedReader reader = new BufferedReader(new FileReader(gitmodules));
		String content = reader.readLine();
		reader.close();
		assertEquals("The first line of .gitmodules file is as expected."
				"[submodule \"foo\"]"
		String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
		String remote = defaultDb.resolve(Constants.HEAD).name();
		assertEquals("The gitlink is same as remote head"
	}

