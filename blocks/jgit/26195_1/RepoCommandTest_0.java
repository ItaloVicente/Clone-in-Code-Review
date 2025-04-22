	@Test
	public void testCopyFileBare() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" revision=\"")
			.append(BRANCH)
			.append("\" >")
			.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
			.append("</project>")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File directory = createTempDirectory("testCopyFileBare");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setURI(remoteDb.getDirectory().toURI().toString());
		Repository localDb = clone.call().getRepository();
		File hello = new File(localDb.getWorkTree()
		assertTrue("The Hello file exists"
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("The Hello file has expected content"
	}

