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
		JGitTestUtil.writeTrashFile(tempDb, "old.xml", xmlContent.toString());
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
		JGitTestUtil.writeTrashFile(tempDb, "new.xml", xmlContent.toString());
		command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/new.xml")
			.setURI(rootUri)
			.call();
		File directory = createTempDirectory("testReplaceManifestBare");
		Repository localDb = Git
			.cloneRepository()
			.setDirectory(directory)
			.setURI(remoteDb.getDirectory().toURI().toString())
			.call()
			.getRepository();
		File hello = new File(localDb.getWorkTree(), "Hello");
		assertFalse("The Hello file shouldn't exist", hello.exists());
		File hellotxt = new File(localDb.getWorkTree(), "Hello.txt");
		assertTrue("The Hello.txt file should exist", hellotxt.exists());
		File dotmodules = new File(localDb.getWorkTree(),
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
