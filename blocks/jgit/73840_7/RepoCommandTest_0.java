	@Test
	public void testRecordShallowRecommendation() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"shallow-please\" ")
					.append("name=\"").append(defaultUri).append("\" ")
					.append("clone-depth=\"1\" />")
				.append("<project path=\"non-shallow\" ")
					.append("name=\"").append(defaultUri).append("\" />")
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecommendShallow(true)
				.call();
			File directory = createTempDirectory("testBareRepo");
			try (Repository localDb = Git.cloneRepository()
					.setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();) {
				File gitmodules = new File(localDb.getWorkTree()
						".gitmodules");
				assertTrue("The .gitmodules file should exist"
						gitmodules.exists());
				FileBasedConfig c = new FileBasedConfig(gitmodules
						FS.DETECTED);
				c.load();
				assertEquals("Recording shallow configuration should work"
						c.getString("submodule"
				assertNull("Recording non shallow configuration should work"
						c.getString("submodule"
			}
		}
	}

