	@Test
	public void testReplaceManifestBare () throws Exception {
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
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/old.xml")
			.setURI(rootUri)
			.call();
		xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"bar\" name=\"")
			.append(defaultUri)
			.append("\" revision=\"")
			.append(BRANCH)
			.append("\" >")
			.append("<copyfile src=\"hello.txt\" dest=\"Hello.txt\" />")
			.append("</project>")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
		command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/new.xml")
			.setOldPath(tempDb.getWorkTree().getAbsolutePath() + "/old.xml")
			.setURI(rootUri)
			.call();
		File directory = createTempDirectory("testReplaceManifestBare");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setURI(remoteDb.getDirectory().toURI().toString());
		Repository localDb = clone.call().getRepository();
		File hello = new File(localDb.getWorkTree()
		assertFalse("The Hello file doesn't exist"
		File hellotxt = new File(localDb.getWorkTree()
		assertTrue("The Hello.txt file exists"
		File dotmodules = new File(localDb.getWorkTree()
				Constants.DOT_GIT_MODULES);
		BufferedReader reader = new BufferedReader(new FileReader(dotmodules));
		boolean foo = false;
		boolean bar = false;
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			if (line.contains("submodule \"foo\""))
				foo = true;
			if (line.contains("submodule \"bar\""))
				bar = true;
		}
		reader.close();
		assertTrue("The bar submodule exists"
		assertFalse("The foo submodule doesn't exist"
	}

