	@Test
	public void testRemoteRevision() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<remote name=\"remote2\" fetch=\".\" revision=\"")
			.append(BRANCH)
			.append("\" />")
			.append("<default remote=\"remote1\" revision=\"master\" />")
			.append("<project path=\"foo\" remote=\"remote2\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("submodule content should be as expected"
				"branch world"
	}

	@Test
	public void testDefaultRemoteRevision() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" revision=\"")
			.append(BRANCH)
			.append("\" />")
			.append("<default remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("submodule content should be as expected"
				"branch world"
	}

