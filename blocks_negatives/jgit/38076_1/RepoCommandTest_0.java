		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				tempDb, "manifest.xml", xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command
			.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File directory = createTempDirectory("testBareRepo");
		Repository localDb = Git
			.cloneRepository()
			.setDirectory(directory)
			.setURI(remoteDb.getDirectory().toURI().toString())
			.call()
			.getRepository();
		File gitmodules = new File(localDb.getWorkTree(), ".gitmodules");
		assertTrue("The .gitmodules file should exist", gitmodules.exists());
		BufferedReader reader = new BufferedReader(new FileReader(gitmodules));
		String content = reader.readLine();
		reader.close();
		assertEquals(
				"The first line of .gitmodules file should be as expected",
				"[submodule \"foo\"]", content);
		String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
		String remote = defaultDb.resolve(Constants.HEAD).name();
		assertEquals("The gitlink should be the same as remote head",
				remote, gitlink);
