	@Test
	public void testGerritUpdateBranch() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"with-branch\" revision=\"another-branch\"")
					.append("<project path=\"with-long-branch\" revision=\"refs/heads/master\"")
					.append("<project path=\"with-default-branch\"")
					.append("<project path=\"with-sha1\" revision=\"0123456789012345678901234567890123456789\"")
					.append("/>")
					.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb1);
			command
				.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setGerritSuperprojectUpdate(true)
				.call();
			ObjectId branchId = remoteDb.resolve("HEAD");

			File directory = createTempDirectory("testGerritUpdateBranch");
			Repository localDb = Git.cloneRepository().setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();

			File dotmodules = new File(localDb.getWorkTree()
					Constants.DOT_GIT_MODULES);
			localDb.close();
			BufferedReader reader = new BufferedReader(new FileReader(
					dotmodules));
			StringBuilder b = new StringBuilder();
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				b.append(line);
			}
			Config c = new Config();
			c.fromText(b.toString());

			assertTrue("standard branches work"
			assertTrue("default branches work"
			assertTrue("long branches work"
			assertTrue("sha1s don't subscribe"
		}
	}

