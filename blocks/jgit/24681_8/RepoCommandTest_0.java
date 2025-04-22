	@Test
	public void testRepoManifestCopyfile() throws Exception {
		Repository localDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\">")
			.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
			.append("</project>")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(localDb
		RepoCommand command = new RepoCommand(localDb);
		command.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(localDb.getWorkTree()
		assertTrue("The original file exists"
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("The original file has expected content"
		hello = new File(localDb.getWorkTree()
		assertTrue("The destination file exists"
		reader = new BufferedReader(new FileReader(hello));
		content = reader.readLine();
		reader.close();
		assertEquals("The destination file has expected content"
	}

