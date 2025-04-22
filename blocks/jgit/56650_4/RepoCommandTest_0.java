	@Test
	public void testRecordRemoteBranch() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"with-branch\" revision=\"another-branch\" name=\"").append(notDefaultUri).append("\" />")
				.append("<project path=\"with-long-branch\" revision=\"refs/heads/master\" name=\"").append(defaultUri).append("\"   />")
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command
				.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecordRemoteBranch(true)
				.call();
			ObjectId branchId = remoteDb.resolve("HEAD");

			File directory = createTempDirectory("testRecordRemoteBranch");
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

			assertTrue("standard branches work for remote"
			assertTrue("default branches work"
		}
	}

	@Test
	public void testRecordRemoteBranchWithTag() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"with-default-branch\" name=\"").append(defaultUri).append("\" remote=\"remote1\" />")
				.append("<project path=\"some-tag\" revision=\"release\" name=\"").append(notDefaultUri).append("\" remote=\"remote1\" />")
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command
				.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecordRemoteBranch(true)
				.call();
			ObjectId branchId = remoteDb.resolve("HEAD");

			File directory = createTempDirectory("testRecordRemoteBranch");
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

			assertTrue("default branches work"
			assertTrue("tags work with remote branches"
		}
	}

