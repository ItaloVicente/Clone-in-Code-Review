		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"foo/bar\" name=\"")
					.append(groupBUri).append("\" />")
					.append("<project path=\"a\" name=\"").append(groupAUri)
					.append("\" />").append("<project path=\"foo\" name=\"")
					.append(defaultUri).append("\" />").append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb, "manifest.xml",
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(
					tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
					.setURI(rootUri).call();
			File directory = createTempDirectory("testRemoveOverlappingBare");
			Repository localDb = Git.cloneRepository().setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();
			File dotmodules = new File(localDb.getWorkTree(),
					Constants.DOT_GIT_MODULES);
			localDb.close();
			BufferedReader reader = new BufferedReader(new FileReader(
					dotmodules));
			boolean foo = false;
			boolean foobar = false;
			boolean a = false;
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.contains("submodule \"foo\""))
					foo = true;
				if (line.contains("submodule \"foo/bar\""))
					foobar = true;
				if (line.contains("submodule \"a\""))
					a = true;
			}
			reader.close();
			assertTrue("The foo submodule should exist", foo);
			assertFalse("The foo/bar submodule shouldn't exist", foobar);
			assertTrue("The a submodule should exist", a);
