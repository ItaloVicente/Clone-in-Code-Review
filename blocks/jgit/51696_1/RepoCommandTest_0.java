	@Test
	public void testTargetBranch() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();
		try {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"foo\" name=\"").append(defaultUri)
					.append("\" />").append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(
					tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
					.setURI(rootUri).setTargetBranch("test").call();
			File directory = createTempDirectory("testBareRepo");
			Repository localDb = Git
					.cloneRepository().setDirectory(directory).setBranch("test")
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
			BufferedReader reader = new BufferedReader(new FileReader(
					gitmodules));
			String content = reader.readLine();
			reader.close();
			assertEquals(
					"The first line of .gitmodules file should be as expected"
					"[submodule \"foo\"]"
			String gitlink = localDb.resolve(Constants.R_HEADS + "test:foo").name();
			localDb.close();
			String remote = defaultDb.resolve(Constants.HEAD).name();
			assertEquals("The gitlink should be the same as remote head"
					remote
		} finally {
			tempDb.close();
			remoteDb.close();
		}
	}

