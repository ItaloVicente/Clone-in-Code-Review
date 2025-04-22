	@Test
	public void testRevisionBare_ignoreTags() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"").append("refs/tags/" + TAG)
				.append("\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"")
				.append(defaultUri)
				.append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testReplaceManifestBare");
		File dotmodules;
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			dotmodules = new File(localDb.getWorkTree()
					Constants.DOT_GIT_MODULES);
			assertTrue(dotmodules.exists());
		}

		String gitModulesContents = Files.readString(dotmodules.toPath()
				UTF_8);
		assertFalse(gitModulesContents.contains("branch"));
	}

