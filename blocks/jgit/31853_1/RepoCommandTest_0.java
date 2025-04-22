	@Test
	public void testNonDefaultRemotes() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<remote name=\"remote2\" fetch=\"")
			.append(notDefaultUri)
			.append("\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("<project path=\"bar\" name=\".\" remote=\"remote2\" />")
			.append("</manifest>");

		Repository localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(
				localDb
		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File file = new File(localDb.getWorkTree()
		assertTrue("We should have foo"
		file = new File(localDb.getWorkTree()
		assertTrue("We should have bar"
	}

