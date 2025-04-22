	@Test
	public void testIncludeTag() throws Exception {
		Repository localDb = createWorkRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<include name=\"_include.xml\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				tempDb

		xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				tempDb

		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(localDb.getWorkTree()
		assertTrue("submodule should be checked out"
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("submodule content should be as expected"
				"master world"
	}

